# Kotlin Script Runner
Kotlin Script Runner is a UI tool that executes Kotlin scripts and shows the output of this execution. The tool is implemented using Java and MVC design pattern
was used. In order to be able to use Kotlin Script Runner, you have to install Kotlin compiler on your device 
(could be found here: https://github.com/JetBrains/kotlin/releases/tag/v1.4.20). It currently just supports Unix bases operating systems.

## How to use?
To run the tool, just head to `Controller.java` class in `controller` package and run it and a window of the tool will open. In the **Editor** pane you have a space to write your
Kotlin script. To execute your script, click on **Run** button and wait for the output to appear in the **Output** pane or errors -if any- in the **Errors**
pane (It may take a little bit of time until the tool processes the script).

The tool also supports highlightin some keywords in Kotlin programming languages. The highlighting is done live while you write the code. Currently supported
keywords are (more will be added soon):
1. `fun`
2. `if`
3. `else`
4. `true`
5. `false`
6. `for`
7. `while`
8. `break`
9. `continue`
10. `in`
