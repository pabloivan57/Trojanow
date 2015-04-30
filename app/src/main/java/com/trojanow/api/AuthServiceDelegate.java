package com.trojanow.api;

import com.trojanow.model.User;

/**
 * Created by pabloivan57 on 4/20/15.
 */
public interface AuthServiceDelegate {
    public void authServiceDidFinishSignup(User user);
    public void authServiceDidFailedSignup(String error);
    public void authServiceDidFinishLogin(User user);
    public void authServiceDidFailedLogin(String error);
}
