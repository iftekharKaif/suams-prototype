import { test, expect } from '@playwright/test';

test.use({ storageState: 'auth/stu-001.json' });   // signed in as stu-001

test('ST-03: a student cannot read results or enter marks', async ({ page }) => {
  const res = await page.request.get('/api/results/stu-002');  // uses stu-001 cookies
  expect(res.status()).toBe(403);

  const marks = await page.request.post('/api/courses/CSE3205/marks',
    { data: { studentId: 'stu-002', classTest: 1, mark: 20 } });
  expect(marks.status()).toBe(403);

  await page.goto('/results/stu-002');
  await expect(page.getByRole('alert')).toContainText('Access denied');
});

test('ST-04: a result stays hidden until it is published', async ({ page }) => {
  const res = await page.request.get('/api/results/stu-001');
  expect(res.status()).toBe(200);
  const body = await res.json();
  expect(body.status).toBe('NOT_PUBLISHED');
  expect(body.marks).toBeUndefined();

  await page.goto('/results/stu-001');
  await expect(page.getByRole('status')).toContainText('Result not yet published');
});
