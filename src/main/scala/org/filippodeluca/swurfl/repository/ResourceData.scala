package org.filippodeluca.swurfl.repository

/**
 *
 * @autor Filippo de Luca
 * @version
 *
 * TODO add isRoot/isPatch
 */
@serializable
@SerialVersionUID(10L)
class ResourceData(val id : String, val devices : Set[DeviceDefinition])