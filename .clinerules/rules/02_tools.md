# Cline.md

This file provides guidance to Cline  when working with code in this repository.

## Build & Test Commands

* As far as available (e.g., via MCP)  ALWAYS use tools to compile and run tests instead of relying on
  bash commands
* after adding a dependency to `build.sbt`, run the
  `import-build` tool
* to lookup a dependency or the latest version, use the
  `find-dep` tool
* to lookup the API of a class, use the `inspect` tool


Use `sbt --client` for all commands. Integration tests use `it:` prefix (sbt shell syntax, not `IntegrationTest/`).

```bash
sbt --client "test; it:test"           # Run all tests (no external services needed)
sbt --client "testOnly *SuiteName"     # Run a single test suite (it:testOnly for integration)
sbt --client bgRun                     # Start the app (requires Kafka + S3/LocalStack)
```

