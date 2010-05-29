package com.supermy.keyvalue;

import java.util.List;

import org.jredis.ClientRuntimeException;
import org.jredis.JRedis;
import org.jredis.ProviderException;
import org.jredis.RedisException;
import org.jredis.protocol.Command;
import org.jredis.ri.alphazero.JRedisClient;
import org.jredis.ri.alphazero.support.DefaultCodec;

import com.supermy.keyvalue.domain.Photo;
import com.supermy.keyvalue.domain.User;

public class DomainDemo {

	public static void main(String[] args) {
		String password = "";
		if (args.length > 0)
			password = args[0];
		new DomainDemo().run(password);
	}

	private void run(String password) {
		try {
			JRedis jredis = new JRedisClient("127.0.0.1", 6379);
			jredis.ping();//
			int objcnt = 100;
			System.out.format("Creating and saving %d Java objects to redis ...", objcnt);
			Photo p = new Photo("相片", "1");
			for (int i = 1; i < objcnt; i++) {
				// instance it
				User user = new User("我" + i, i+"");
				user.setPhoto(p);
				// get the next available object id from our Redis counter using INCR command
				//long id = jredis.incr("User::next_id");
				// we can bind it a unique key using map (Redis "String") semantics now
				//String key = "objects::User::" + id;
				// voila: java object db
				//jredis.set(key, user);
				// and lets add it to this set too since this is so much fun
				jredis.sadd("users", user);
			}
			System.out.format(" and done.\n");

			List<User> members = DefaultCodec.decode(jredis.smembers("users"));
			for (User user : members) {
				System.out.println("id:" + user.getId());
				System.out.println("name:" + user.getName());
				System.out.println("id:" + user.getPhoto().getName());
			}

			jredis.quit();
		} catch (RedisException e) {
			if (e.getCommand() == Command.PING) {
				System.out
						.format("I'll need that password!  Try again with password as command line arg for this program.\n");
			}
		} catch (ProviderException e) {
			System.out.format("Oh no, an 'un-documented feature':  %s\nKindly report it.", e.getMessage());
		} catch (ClientRuntimeException e) {
			System.out.format("%s\n", e.getMessage());
		}
	}
}
