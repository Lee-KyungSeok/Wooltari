package kr.co.wooltari.domain.user;

/**
 * Created by anya on 2017. 12. 8..
 */

public class Login
{
    private String token;

    private User user;

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [token = "+token+", user = "+user+"]";
    }
}

