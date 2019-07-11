# MathTrainer
MathTrainer is a quick and bare-featured trainer for mental math written in Java. 
You can control question type, question frequency (sort of), and number of questions per
set; MathTrainer will give you a summary of time spent per question as well as average
time overall after each set.

## Setting up
Compile with `javac MathTrainer.java`; run with `java MathTrainer <options>`.
No external dependencies or configuring necessary.

## Usage
```
java MathTrainer <question-types> <num-questions>
```
`num-questions` must be a valid integer number.
`question-types` is a string where each character corresponds to a certain type of question.
The order of characters does not matter, and you can control question frequency to some
extent by adding more of a certain character than others.
The table below details the type of question each character corresponds to.

|  character | question type  | notes  |
| :------------: | :------------: | :------------: |
| `a` | addition  | Generates two integers that you must add together.  |
| `b`  | cbrt  | Generates cubed two-digit numbers, from which you have to take the cube root of to determine the original two-digit number.  |
| `c`  | combination  | Generates a combination problem (`a C b`)  |
| `d`  | division  | Generates a division problem.  Answers will always be integral.  |
| `i`  | primes  | Generates random numbers, which you have to decide are prime or not prime. |
| `m`  | multiplication  | Generates two integers that you must multiply together.  |
| `p`  | permutation  | Generates a permutation problem (`a P b`)  |
| `q`  | sqrt  | Generates squared two-digit numbers, from which you have to take the square root of to determine the original two-digit number.  |
| `r`  |  rote | Quizzes you on certain constants or theories that are helpful to know.  |
| `s`  | subtraction  | Generates two integers that you must subtract.  The answer will never be less than zero.  |
| `t`  | trig  | Generates common 30-45-60-90 trig functions in both radians and degrees for sin, cos, tan, sec, and csc.  |

## Extending and customizing
To extend and add your own type of question, implement the `QuestionGenerator ` interface and then add your problem type to the big if-else block in `main()`.
I'm not going to add features for customizing on the fly because I think it's unnecessary
for my purposes and it'll make actually using the program a pain.  Any customizing you
want to do, ex. modifying upper limits on problems to increase or decrease difficulty,
you must make in the source code yourself and recompile.  It should be fairly easy
if you know what you're doing.

