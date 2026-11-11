# SUAMS prototype

A small prototype for the CSE 3205 technical proposal on the Smart University Academic
Management System (SUAMS). It holds the parts of the Course Registration and Examination
Result module that the proposal tests, and runs the `suams-ci` pipeline from Part C.

The Java project targets Java 25 (LTS). Use a Java 25 JDK to run its Maven build and tests.

| Folder | What it holds |
|---|---|
| `src/main/java/.../registration` | `RegistrationService.addCourse`, `PrerequisiteChecker`, the registration calendar (rules R1, R2 and R4) |
| `src/main/java/.../grading` | The Strategy pattern for grading: `GradingPolicy`, `TheoryCoursePolicy`, `PolicyRegistry`, `ResultProcessor` (rules R5 and R6) |
| `src/test/java` | JUnit tests UT-01 to UT-10, BB-01 to BB-10 and WB-01 to WB-06 |
| `prototype/server.js` | A tiny Node server with the result-access rules of scenario S3 |
| `e2e` | Playwright system tests ST-03 and ST-04 |
| `.github/workflows/suams-ci.yml` | The pipeline: `verify`, `e2e` and `release` |

Run the Java tests locally with `mvn -B verify`; the coverage report is written to
`target/site/jacoco/index.html`. GitHub Actions uploads the same report, and the Playwright
HTML report, as artifacts of every run.
