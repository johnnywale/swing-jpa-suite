package com.jovx.app;

import java.util.logging.Logger;

import org.hsqldb.Server;

public class H2DbService {
	protected final Logger logger = Logger.getLogger("com.jovx.H2DbService");

	public void init() {
		logger.info("Starting database");
		String[] ags = new String[] { "-database.0", "file:mydb",
				"-dbname.0 xdb" };
		Server.main(ags);
	}
}
