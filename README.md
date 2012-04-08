Unofficial WURFL Scala API
==========================
It is based on a customized Patricia Trie implementation

Exaples
-------

### Basic
    val wurfl = Wurfl("classpath:///root.xml").build()
    wurfl.device(Headers("user-agent"->List("Mozilla"))){device=>
      val xhtmlSupportLevel = device("xhtml_support_level")
      // ...
    }

### Patching
    val patched = wurfl.patch("classpath:///patch_one.xml")
Download
--------
scala-wurfl is available to the Sonatype OSS Maven repository:

 *    Snapshots [http://oss.sonatype.org/content/repositories/snapshots/](http://oss.sonatype.org/content/repositories/snapshots/)
 *    Releases [http://oss.sonatype.org/content/repositories/releases/](http://oss.sonatype.org/content/repositories/releases/)