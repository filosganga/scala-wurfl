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
