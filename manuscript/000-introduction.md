
{language=java, line-numbers=off}


# Introduction

## What will we learn?

 - Driving development from acceptance tests
 - Writing end-to-end tests
 - Driving development from true unit tests
 - How to keep the pure logic of the application separate from Android APIs (aka the ports-and-adapters architecture aka hexagonal architecture)
 - How to use mocks properly (aka the London Style)
 - Presenter-first development
 - What is the "main partition" and why it's useful
 - A bit of object-oriented design

We will not discuss continuous integration or version control.

## Our philosophy

This will not be a "stream-of-consciousness".  We will present the samples of code as if we got them right the first time.  We didn't, but we think that reading a detailed account of our mistakes and false starts would be tedious.  We show you instructive examples, presented in a logical flow, so that you get our idea of what good code looks like.  Just don't worry if you can't get your code right the first time like it seems we are doing.  We don't usually get it right the first time either!



## What this book is not about

We will not provide extensive information on how to test your application on different versions of Android, different devices or different form factors.  This is a book about software design, not testing.  We believe that TDD is both a design method and a way to write correct code; however in this book we want to emphasize design.



## Assumptions

 - You know Java
 - You have built and run an Android application before
 - You have a basic understanding of what testing is and how JUnit works
 - You use Android Studio

