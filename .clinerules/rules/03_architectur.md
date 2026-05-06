# Cline.md

This file provides guidance to Cline when working with code in this repository.


## Code Style

- **Scala 3.8**, braceless syntax preferred (`:` indentation, e.g., `supervised:`, `.map: x =>`)
- **Scalafmt** with `maxColumn = 120`, dialect `scala3`
- **Visibility** — `private[kafka]` is useful, but `private[scalakafkasoap]` is pointless since it's the root package
- **Immutable state** — prefer threading immutable state through methods over mutable class fields
- **Test framework**: MUnit (`munit.FunSuite`)

## Key Libraries

- **cats**  — abstractions for functional programming in Scala
- **cats-effect**  — pure asynchronous runtime for Scala
- **circe**  — handle JSON data
- **fs2**  — stream processing and structured concurrency
- **ScalaFX**  — for building desktop guis
- **http4s**  — interface for HTTP services
- **skunk**  — JDBC layer for Scala
- **munit**  —  testing library
- **scalaxb** — XSD-to-Scala codegen

## Testing

- Unit tests are located in `src/test/`
- Integration tests  are located in `src/it/`
- When necessarry test classes that live in `src/test/` are shared with `src/it/` via `IntegrationTest / dependencyClasspath` in build.sbt
