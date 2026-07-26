import time
import uuid
import logging
from fastapi import Request
from starlette.middleware.base import BaseHTTPMiddleware

logger = logging.getLogger(__name__)

class RequestLoggingMiddleware(BaseHTTPMiddleware):
    """
    Middleware that generates request IDs, logs request parameters,
    and calculates backend transaction latency.
    """
    async def dispatch(self, request: Request, call_next):
        request_id = request.headers.get("X-Request-ID") or str(uuid.uuid4())
        
        # Store request ID in state for downstream routes
        request.state.request_id = request_id
        
        start_time = time.time()
        logger.info(f"[{request_id}] Incoming request: {request.method} {request.url.path}")
        
        try:
            response = await call_next(request)
            
            process_time = (time.time() - start_time) * 1000
            logger.info(
                f"[{request_id}] Request completed: {request.method} {request.url.path} "
                f"- Status: {response.status_code} - Latency: {process_time:.2f}ms"
            )
            
            # Inject trace header in response
            response.headers["X-Request-ID"] = request_id
            return response
            
        except Exception as e:
            process_time = (time.time() - start_time) * 1000
            logger.error(
                f"[{request_id}] Request crashed: {request.method} {request.url.path} "
                f"- Error: {str(e)} - Latency: {process_time:.2f}ms"
            )
            raise e
