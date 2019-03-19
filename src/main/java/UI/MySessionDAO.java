package UI;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;

import java.io.Serializable;
import java.util.Collection;

//to test save session
public class MySessionDAO extends MemorySessionDAO {
    public MySessionDAO() {
        System.out.println("11111111111111111111111111111111111111");
    }

    @Override
    protected Serializable doCreate(Session session) {
        return super.doCreate(session);
    }

    @Override
    protected Session storeSession(Serializable id, Session session) {
        return super.storeSession(id, session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return super.doReadSession(sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        super.update(session);
    }

    @Override
    public void delete(Session session) {
        super.delete(session);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return super.getActiveSessions();
    }


}
