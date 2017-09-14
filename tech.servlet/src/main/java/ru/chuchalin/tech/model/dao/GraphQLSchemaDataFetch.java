package ru.chuchalin.tech.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.jpa.QueryHints;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class GraphQLSchemaDataFetch {
	private HibernateUtil dataSession;

	public GraphQLSchemaDataFetch(HibernateUtil _dataSession) {
		dataSession = _dataSession;
	}

	public DataFetcher eventFetcher = new DataFetcher() {
		@Override
		public Object get(DataFetchingEnvironment environment) {
			List resultList = new ArrayList();
			if (dataSession != null && dataSession.getSessionFactory() != null) {
				Session sf = null;
				try {
					sf = dataSession.getSessionFactory().openSession();
					if (sf != null) {
						if (!environment.containsArgument("id"))
							resultList = sf.createQuery("from Event").setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
						else
							resultList = sf.createQuery("from Event where eventID = " + environment.getArgument("id"))
									.setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
					}
				} catch (Exception e) {
				} finally {
					if (sf != null && sf.isOpen()) {
						sf.close();
					}
				}
			}
			return resultList;
		}
	};

	public DataFetcher profileFetcher = new DataFetcher() {
		@Override
		public Object get(DataFetchingEnvironment environment) {
			List resultList = new ArrayList();
			if (dataSession != null && dataSession.getSessionFactory() != null) {
				Session sf = null;
				try {
					sf = dataSession.getSessionFactory().openSession();
					if (sf != null) {
						if (!environment.containsArgument("id"))
							resultList = sf.createQuery("from Profile").setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
						else
							resultList = sf
									.createQuery("from Profile where profileID = " + environment.getArgument("id"))
									.setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
					}
				} catch (Exception e) {
				} finally {
					if (sf != null && sf.isOpen()) {
						sf.close();
					}
				}
			}
			return resultList;
		}
	};

	public DataFetcher cityFetcher = new DataFetcher() {
		@Override
		public Object get(DataFetchingEnvironment environment) {
			List resultList = new ArrayList();
			if (dataSession != null && dataSession.getSessionFactory() != null) {
				Session sf = null;
				try {
					sf = dataSession.getSessionFactory().openSession();
					if (sf != null) {
						if (!environment.containsArgument("id"))
							resultList = sf.createQuery("from City").setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
						else
							resultList = sf.createQuery("from City where cityID = " + environment.getArgument("id"))
									.setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
					}
				} catch (Exception e) {
				} finally {
					if (sf != null && sf.isOpen()) {
						sf.close();
					}
				}
			}

			return resultList;
		}
	};

	public DataFetcher musicStyleFetcher = new DataFetcher() {
		@Override
		public Object get(DataFetchingEnvironment environment) {
			List resultList = new ArrayList();
			if (dataSession != null && dataSession.getSessionFactory() != null) {
				Session sf = null;
				try {
					sf = dataSession.getSessionFactory().openSession();
					if (sf != null) {
						if (!environment.containsArgument("id"))
							resultList = sf.createQuery("from EventMusicStyle").setHint(QueryHints.HINT_FETCH_SIZE, 10)
									.list();
						else
							resultList = sf
									.createQuery(
											"from EventMusicStyle where styleID = " + environment.getArgument("id"))
									.setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
					}
				} catch (Exception e) {
				} finally {
					if (sf != null && sf.isOpen()) {
						sf.close();
					}
				}
			}
			return resultList;
		}
	};

	public DataFetcher eventAddressFetcher = new DataFetcher() {
		@Override
		public Object get(DataFetchingEnvironment environment) {
			List resultList = new ArrayList();
			if (dataSession != null && dataSession.getSessionFactory() != null) {
				Session sf = null;
				try {
					sf = dataSession.getSessionFactory().openSession();
					if (sf != null) {
						if (!environment.containsArgument("id"))
							resultList = sf.createQuery("from EventAddress").setHint(QueryHints.HINT_FETCH_SIZE, 10)
									.list();
						else
							resultList = sf
									.createQuery(
											"from EventAddress where eventAddressID = " + environment.getArgument("id"))
									.setHint(QueryHints.HINT_FETCH_SIZE, 10).list();
					}
				} catch (Exception e) {
				} finally {
					if (sf != null && sf.isOpen()) {
						sf.close();
					}
				}
			}
			return resultList;
		}
	};
}
