package ru.chuchalin.tech.model.dao;

public class DataAccess {
	private HibernateUtil dataSession;
	private GraphQLSchemaModel gql;
	private Auth auth;
	
	public DataAccess(String url, String login, String pass){
		dataSession = new HibernateUtil().init(url, login, pass);
		gql = new GraphQLSchemaModel(dataSession);
		auth = new Auth(dataSession);
	}
	
	public GraphQLSchemaModel getGraphQLSchema(){
		return gql;
	}
	
	public Auth getAuth(){
		return auth;
	}

}
