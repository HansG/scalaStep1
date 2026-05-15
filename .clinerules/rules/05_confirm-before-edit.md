# Rule: confirm-before-edit
# Description: Always ask the user before modifying or creating files

# Rule
Before writing, modifying, or deleting **any file**, ALWAYS use `ask_followup_question`
to describe exactly what changes are planned and wait for explicit user confirmation.

## What to ask
- Which file(s) will be changed
- What exactly will be added, removed, or modified (brief summary)
- Why (only if not obvious from context)

## Exceptions
- Shell commands that read-only (e.g. `cat`, `grep`, `ls`) do NOT need confirmation
- If the user's request is itself an explicit instruction to write a specific file
  (e.g. "create file X with content Y"), the request counts as confirmation —
  no separate question needed

## Example
Instead of immediately calling `write_to_file` or `replace_in_file`, first ask:
> "Ich möchte in `build.sbt` die `scalaVersion` von `3.7.4` auf `3.8.3` ändern. Soll ich das tun?"
