import logging
import sys
from app.core.config import settings

def setup_logging():
    """
    Sets up application logging format and thresholds.
    """
    log_format = "%(asctime)s - %(name)s - %(levelname)s - [%(filename)s:%(lineno)d] - %(message)s"
    
    # Configure level based on mode
    log_level = logging.DEBUG if settings.ENV == "development" else logging.INFO
    
    logging.basicConfig(
        level=log_level,
        format=log_format,
        handlers=[
            logging.StreamHandler(sys.stdout)
        ],
        force=True  # Clear existing configurations
    )
    
    # Reduce verbose logger noise from third-party libraries
    logging.getLogger("google").setLevel(logging.WARNING)
    logging.getLogger("urllib3").setLevel(logging.WARNING)
    
    logger = logging.getLogger(__name__)
    logger.info(f"Logging successfully initialized with level: {logging.getLevelName(log_level)}")
