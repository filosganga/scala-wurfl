package org.filippodeluca.swurfl.repository

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 09.14
 * To change this template use File | Settings | File Templates.
 */


@serializable
@SerialVersionUID(10L)
class ResourceData(val id : String, val devices : Set[DeviceDefinition])