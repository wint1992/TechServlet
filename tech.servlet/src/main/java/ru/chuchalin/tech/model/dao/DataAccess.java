package ru.chuchalin.tech.model.dao;

public class DataAccess {
	private HibernateUtil dataSession;
	private GraphQLSchemaModel gql;
	private Auth authAccess;
	private DBSession dbSessionAccess;
	private UpdateData ud;

	public DataAccess(String url, String login, String pass) {
		dataSession = new HibernateUtil().init(url, login, pass);
		gql = new GraphQLSchemaModel(dataSession);
		authAccess = new Auth(dataSession);
		dbSessionAccess = new DBSession(dataSession);
		ud = new UpdateData(dataSession);
	}

	public GraphQLSchemaModel getGraphQLSchema() {
		return gql;
	}

	public Auth getAuthAccess() {
		return authAccess;
	}

	public DBSession getDBSessionAccess() {
		return dbSessionAccess;
	}
	
	public UpdateData getUpdater() {
		return ud;
	}

}
