Unofficial WURFL Scala API
==========================
This is an unofficial [WURFL](http://wurfl.sourceforge.net/) Scala API. It is based on a custom Patricia Trie
implementation that is fast but without the precision of the official APIs.

scala-wurfl is available to the Sonatype OSS Maven repository for Scala 2.10.0, 2.10.2:

 *    Snapshots [http://oss.sonatype.org/content/repositories/snapshots/](http://oss.sonatype.org/content/repositories/snapshots/)
 *    Releases [http://oss.sonatype.org/content/repositories/releases/](http://oss.sonatype.org/content/repositories/releases/)

[![The Build Status](https://travis-ci.org/filosganga/scala-wurfl.png?branch=master)](https://travis-ci.org/filosganga/scala-wurfl)

Quick Start
-----------
The Scala WURFL API uses the [typesafe config library](https://github.com/typesafehub/config) for the initialization. It
is shipped with a default configuration and default dataset (last available).

The entrypoint for the API is the Wurfl object. To obtain a Wurfl object with the default configuration and dataset:
    val wurfl = Wurfl().build()

If you want to pass a different configuration:

    val configuration = ???

    val wurfl = Wurlf(configuration).build()

If you want to pass a different dataset without using the typesafe config:

    val wurfl = Wurfl("classpath:/root.xml").build()

Or using patches:

    val wurfl = Wurfl("classpath:/root.xml")
          .withPatch("classpath:/patch_a.xml")
          .withPatch("classpath:/patch_b.xml")
          .build()

Once you have obtained your Wurfl instance you can obtain a device passing an Headers instance. There is a shortcut to
build an Headers from an User-Agent string:

    val device = wurfl.device(Headers.userAgent("Mozilla..."))

Or building a full featured Headers:

    val device = wurfl.device(Headers("User-Agent"->"Mozilla...", "X-WAP-Profile"->"..."))

You can also apply a patch to an existing Wurfl object (it doesn't modify the current Wurfl instance):

    val patched = wurfl.patch("classpath:/patch_one.xml")
