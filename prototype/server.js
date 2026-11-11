// Small prototype of the SUAMS result pages and APIs, used by the Playwright system tests.
// It applies the access rules of scenario S3: a student sees only his or her own result,
// only after it is published, and can never enter marks. Sessions come from the "sid" cookie.
const http = require('node:http');

const PORT = 8080;
const published = new Set();                 // courses whose results are published (none yet)

function userOf(req) {
  const match = /(?:^|;\s*)sid=([^;]+)/.exec(req.headers.cookie || '');
  return match ? decodeURIComponent(match[1]) : null;
}

function audit(user, action) {
  console.log(`${new Date().toISOString()} AUDIT ${user} ${action}`);
}

function sendJson(res, status, body) {
  res.writeHead(status, { 'Content-Type': 'application/json' });
  res.end(JSON.stringify(body));
}

function sendPage(res, status, title, role, message) {
  res.writeHead(status, { 'Content-Type': 'text/html; charset=utf-8' });
  res.end(`<!doctype html><html lang="en"><head><meta charset="utf-8"><title>${title}</title></head>` +
          `<body><h1>${title}</h1><div role="${role}">${message}</div></body></html>`);
}

const server = http.createServer((req, res) => {
  req.resume();                              // request bodies are not needed here
  const url = new URL(req.url, `http://localhost:${PORT}`);
  if (url.pathname === '/health') return sendJson(res, 200, { status: 'up' });

  const user = userOf(req);
  if (!user) return sendJson(res, 401, { error: 'Sign in first' });
  const isStudent = user.startsWith('stu-');

  let m = url.pathname.match(/^\/api\/results\/([\w-]+)$/);
  if (req.method === 'GET' && m) {
    if (isStudent && m[1] !== user) {
      audit(user, `read the result of ${m[1]}: refused`);
      return sendJson(res, 403, { error: 'Access denied' });
    }
    if (!published.has('CSE 3205')) {
      return sendJson(res, 200, { student: m[1], course: 'CSE 3205', status: 'NOT_PUBLISHED' });
    }
    return sendJson(res, 200, { student: m[1], course: 'CSE 3205', status: 'PUBLISHED', marks: {} });
  }

  m = url.pathname.match(/^\/api\/courses\/([\w-]+)\/marks$/);
  if (req.method === 'POST' && m) {
    if (isStudent) {
      audit(user, `enter marks for ${m[1]}: refused`);
      return sendJson(res, 403, { error: 'Access denied' });
    }
    return sendJson(res, 201, { course: m[1], saved: true });
  }

  m = url.pathname.match(/^\/results\/([\w-]+)$/);
  if (req.method === 'GET' && m) {
    if (isStudent && m[1] !== user) {
      audit(user, `open the result page of ${m[1]}: refused`);
      return sendPage(res, 403, 'Result', 'alert', 'Access denied');
    }
    const message = published.has('CSE 3205') ? 'Result published' : 'Result not yet published';
    return sendPage(res, 200, 'Result', 'status', message);
  }

  return sendJson(res, 404, { error: 'Not found' });
});

server.listen(PORT, () => console.log(`SUAMS prototype listening on port ${PORT}`));
