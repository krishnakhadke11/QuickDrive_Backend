package com.TransportationService.config;

import com.TransportationService.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            @Lazy HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);

            /*
                This is used to check if the user is already authenticated or not
                If that user is already authenticated then there is no need to authenticate again
                (So if the user is not authenticated it will return null)
             */
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userEmail != null && authentication == null) {
                //To extract the userDetails From the username
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                /*
                    In this context, null is used for the credentials because:
                    The authentication process has already occurred (via JWT validation).
                    The actual password is not needed or available at this point.
                    It's a security best practice not to keep the password in memory longer than necessary.
                 */
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    /*
                    It is used to add more information to the Authentication object (UsernamePasswordAuthenticationToken authToken)
                    with additional details about the current http request
                    Details such as : Remote Address, Session ID, Request Parameters, User Agent:Info about the browser
                    Use Case : Logging and Auditing
                     */
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //Setting the SecurityContext will prevent re-authentication
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    Integer id = jwtService.getIdFromToken(jwt);

                    request.setAttribute("id", id);
                }
            }
            //It is used to pass it to the next filter or Middleware
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
