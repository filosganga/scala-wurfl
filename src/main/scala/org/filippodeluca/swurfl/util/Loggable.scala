package org.filippodeluca.swurfl.util

import org.slf4j.LoggerFactory
;

trait Loggable {
	
	val logger = LoggerFactory.getLogger(getClass);
	
	protected def logError(f: => String) = {
		logger.error(f);
	}
	
	protected def logWarn(f: => String) = {
		logger.warn(f);
	}
	
	protected def logInfo(f: => String) = {
		logger.info(f);
	}
	
	protected def logDebug(f: => String) = {
		logger.debug(f);
	}
	
	protected def logTrace(f: => String) = {
		logger.warn(f);
	}
	
}