
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

This will not be a "stream-of-consciousness".  We will present the samples of code as if we got them right the first time.  We didn't, but we think that reading a detailed account of our mistakes and false starts would be tedious.  We show you instructive examples, presented in a logical flow, so that you get our idea of what good code looks like.  Just don't worry if you can't get your code right the first time like it seems we are doing.  We, the authors, don't usually get it right the first time either!

We value simplicity.  We know about the serveral tools and library to deal with dependency problems for Android projects.  We looked at them, but in the end decided not to use them... at least for the moment, because they are still too complicated, fragile or simply not that useful.  We prefer to download as few tools and libraries as possible. Our approach depends very little on esoteric external tools, but will use the standard Android development enviromnent, and a few generic Java tools such as JMock.

We use Android Studio because this is what Google suggests developers to use.  You can follow our book with Eclipse.

%% THIS SHOULD NOT BE VISIBLE


## What this book is not about

We will not provide extensive information on how to test your application on different versions of Android, different devices or different form factors.  This is a book about software design, not testing.  We believe that TDD is both a design method and a way to write correct code; however in this book we want to emphasize design.

Other sources on testing Android exists (see ....)


## Assumptions

 - You know Java
 - You have built and run an Android application before
 - You have a basic understanding of what testing is and how JUnit works
 - You use Android Studio

