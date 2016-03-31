package com.dndtool.server.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private CredentialsService credentialService;

    @Autowired
    public RegistrationController(CredentialsService credentialService) {
        this.credentialService = credentialService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    // @MKovalchik - You know why @ModelAttribute works, but @RequestBody doesn't? I thought we typically use RequestBody for this?
    // Perhaps a misconfiguration somewhere?
    public void registerUser(@ModelAttribute RegistrationDetails registrationRequest) throws UserExistsException, MissingUserException {
        LOGGER.debug("Received request to register user with name: " + registrationRequest.getUserName());
        if (registrationRequest.getUserName() == null) {
            throw new MissingUserException();
        }
        else {
            if (credentialService.userExists(registrationRequest.getUserName())) {
                LOGGER.error("Attempt to register user: " + registrationRequest.getUserName() + " failed. User already exists.");
                throw new UserExistsException();
            }
            else {
                credentialService.registerUser(registrationRequest.getUserName(), registrationRequest.getPassword());
            }
        }
    }

    /**
     * Exception thrown by this controller when an attempt to register a user that already exists happens.
     */
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    private static class UserExistsException extends Exception {
        private static final long serialVersionUID = -2665713768086851163L;
    }
    
    /**
     * Exception thrown by this controller when an attempt to register a user with no user name happens.
     */
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    private static class MissingUserException extends Exception {
        private static final long serialVersionUID = -2665713768086851163L;
    }

}
