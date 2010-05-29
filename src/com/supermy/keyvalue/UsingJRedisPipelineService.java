package com.supermy.keyvalue;
import org.jredis.JRedis;
import org.jredis.RedisException;
import org.jredis.connector.ConnectionSpec;
import org.jredis.ri.alphazero.JRedisClient;
import org.jredis.ri.alphazero.JRedisPipelineService;
import org.jredis.ri.alphazero.connection.DefaultConnectionSpec;
import org.jredis.ri.alphazero.support.Log;

import static org.jredis.ri.alphazero.support.DefaultCodec.*;

/**
 * JRedisPipelineService provides blocking synchronous semantics backed by a pipeline and is suitable
 * for concurrent usages with a single (socket) connection to the server.  There is really nothing
 * different here than using a vanial {@link JRedisClient}.
 *
 * @author  Joubin Houshyar (alphazero@sensesay.net)
 * @version alpha.0, Nov 6, 2009
 * @since   alpha.0
 * 
 */

public class UsingJRedisPipelineService {

	final JRedis jredis;
	private UsingJRedisPipelineService() {
		// same as usual.
		ConnectionSpec spec = DefaultConnectionSpec.newSpec();
		spec.setDatabase(11).setCredentials("jredis".getBytes());

		// only need to use the specific class.
		jredis = new JRedisPipelineService(spec);
	}

    private  void run () {
    	try {
	        jredis.ping();
	        basicStuff();
	        elicitErrors();
        }
        catch (RedisException e) {
	        e.printStackTrace();
        }
        finally {
        	jredis.quit();
        }
    }
    
    private void elicitErrors ()  {
        String key = "foo"	;
        try {
	        jredis.set(key, "bar");
	        jredis.sadd(key, "foobar");
        }
        catch (RedisException e) {
        	Log.log("Expected elicited error: %s", e.getMessage());
        }
    }

	private void basicStuff () throws RedisException {
        jredis.flushdb();
        String key = "foo"	;
        jredis.set(key, "bar");
        String value = toStr(jredis.get(key));
        System.out.format("%s => %s\n", key, value);
    }

	public static void main (String[] args) {
		(new UsingJRedisPipelineService()).run();
	}
}