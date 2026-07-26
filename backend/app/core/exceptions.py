import logging
from fastapi import FastAPI, Request, status
from fastapi.responses import JSONResponse
from fastapi.exceptions import RequestValidationError

logger = logging.getLogger(__name__)

class AuraException(Exception):
    """
    Base exception class for all Aura AI errors.
    """
    def __init__(
        self,
        status_code: int = status.HTTP_500_INTERNAL_SERVER_ERROR,
        error_code: str = "INTERNAL_SERVER_ERROR",
        message: str = "An unexpected error occurred.",
        details: dict = None
    ):
        super().__init__(message)
        self.status_code = status_code
        self.error_code = error_code
        self.message = message
        self.details = details

class AuthenticationError(AuraException):
    def __init__(self, message: str = "Authentication failed.", details: dict = None):
        super().__init__(
            status_code=status.HTTP_401_UNAUTHORIZED,
            error_code="UNAUTHORIZED",
            message=message,
            details=details
        )

class ResourceNotFoundError(AuraException):
    def __init__(self, message: str = "Requested resource not found.", details: dict = None):
        super().__init__(
            status_code=status.HTTP_404_NOT_FOUND,
            error_code="NOT_FOUND",
            message=message,
            details=details
        )

class DatabaseConnectionError(AuraException):
    def __init__(self, message: str = "Database operation failed.", details: dict = None):
        super().__init__(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            error_code="DATABASE_ERROR",
            message=message,
            details=details
        )

class AIServiceError(AuraException):
    def __init__(self, message: str = "AI Engine failed to generate response.", details: dict = None):
        super().__init__(
            status_code=status.HTTP_502_BAD_GATEWAY,
            error_code="AI_ENGINE_ERROR",
            message=message,
            details=details
        )

# Exception handlers registration
def register_exception_handlers(app: FastAPI):
    
    @app.exception_handler(AuraException)
    async def aura_exception_handler(request: Request, exc: AuraException):
        logger.error(f"AuraException [{exc.error_code}]: {exc.message}")
        return JSONResponse(
            status_code=exc.status_code,
            content={
                "error": {
                    "code": exc.error_code,
                    "message": exc.message,
                    "details": exc.details
                }
            }
        )

    @app.exception_handler(RequestValidationError)
    async def validation_exception_handler(request: Request, exc: RequestValidationError):
        logger.warning(f"Request Validation failed: {exc.errors()}")
        return JSONResponse(
            status_code=status.HTTP_422_UNPROCESSABLE_ENTITY,
            content={
                "error": {
                    "code": "VALIDATION_ERROR",
                    "message": "Input validation failed.",
                    "details": exc.errors()
                }
            }
        )

    @app.exception_handler(Exception)
    async def general_exception_handler(request: Request, exc: Exception):
        logger.critical(f"Unhandled Exception: {str(exc)}", exc_info=True)
        return JSONResponse(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            content={
                "error": {
                    "code": "INTERNAL_SERVER_ERROR",
                    "message": "A critical system error occurred.",
                    "details": None
                }
            }
        )
