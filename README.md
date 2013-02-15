Unofficial WURFL Scala API
==========================
This is an unofficial [WURFL](http://wurfl.sourceforge.net/) Scala API. It is based on a custom Patricia Trie
implementation that is fast but not exact as the official APIs are.

It is available for Scala 2.8.2, 2.9.2, 2.10.0.

Examples
--------

### Basic
    val wurfl = Wurfl("classpath:/root.xml").build()
    val device = wurfl.device(Headers.userAgent("Mozilla"))

    val xhtmlSupportLevel = device("xhtml_support_level")

    device.get("xhtml_support_level").map{x=>
        / ...
    }


### Patching

    val wurfl = Wurfl("classpath:/root.xml")
      .withPatch("classpath:/patch_a.xml")
      .withPatch("classpath:/patch_b.xml")
      .build()

    val wurfl = Wurfl("classpath:/root.xml")
      .withPatches("classpath:/patch_a.xml", "classpath:/patch_b.xml")
      .build()

    val patched = wurfl.patch("classpath:/patch_one.xml")
Download
--------
scala-wurfl is available to the Sonatype OSS Maven repository:

 *    Snapshots [http://oss.sonatype.org/content/repositories/snapshots/](http://oss.sonatype.org/content/repositories/snapshots/)
 *    Releases [http://oss.sonatype.org/content/repositories/releases/](http://oss.sonatype.org/content/repositories/releases/)