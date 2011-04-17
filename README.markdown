Unofficial WURFL Scala API
==========================
It is based upon [Roger Kapsi Patricia-Trie](https://github.com/rkapsi/patricia-trie) at moment, but a new version with
specialized trie is at development.

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
