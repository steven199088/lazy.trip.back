package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import article.model.ArticleVO;
import common.HikariDataSource;

public class ArticleDAO implements ArticleDAO_interface {

//	private static DataSource ds = null;
//	static {
//		try {
//			Context ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//	}

	private static final String INSERT_STMT =
			"insert into article(article_title,article_content,article_date,article_date_change,article_image,admin_id,member_id,tour_id) values(?,?,?,?,?,?,?,?)";
	private static final String UPDATE = 
			"UPDATE article set article_title=?,article_content=?,article_date_change=?,article_image=? where article_id=?;";
	private static final String DELETE = 
			"DELETE FROM article where article_id = ?";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM article where article_id = ?";
	private static final String GET_MEMBER_STMT=
			"select * from article where member_id = ?";
	private static final String GET_ALL_STMT = 
			"SELECT * FROM article order by article_id";
	
	private static final String SEARCH = 
			"select * from article where article_title like ?";
	
	@Override
	public void insert(ArticleVO articleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = HikariDataSource.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, articleVO.getArticleTitle());
			pstmt.setString(2, articleVO.getArticleContent());
			pstmt.setTimestamp(3, articleVO.getArticleDate());
			pstmt.setTimestamp(4, null);
			pstmt.setBytes(5, articleVO.getArticleImage());
			pstmt.setInt(6, articleVO.getAdminId());
			pstmt.setInt(7, articleVO.getMemberId());
			pstmt.setInt(8, articleVO.getTourId());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(ArticleVO articleVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = HikariDataSource.getConnection();;
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, articleVO.getArticleTitle());
			pstmt.setString(2, articleVO.getArticleContent());
			pstmt.setTimestamp(3, articleVO.getArticleDateChange());
			pstmt.setBytes(4, articleVO.getArticleImage());
			pstmt.setInt(5, articleVO.getArticleId());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(Integer articleId) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = HikariDataSource.getConnection();;
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, articleId);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public ArticleVO findByPrimaryKey(Integer articleId) {
		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = HikariDataSource.getConnection();;
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, articleId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// articleVo 也稱為 Domain objects
				articleVO = new ArticleVO();
				articleVO.setArticleId(rs.getInt("article_id"));
				articleVO.setArticleTitle(rs.getString("article_title"));
				articleVO.setArticleContent(rs.getString("article_content"));
				articleVO.setArticleDate(rs.getTimestamp("article_date"));
				articleVO.setArticleDateChange(rs.getTimestamp("article_date_change"));
				articleVO.setArticleImage(rs.getBytes("article_image"));
				articleVO.setMemberId(rs.getInt("member_id"));
				articleVO.setTourId(rs.getInt("tour_id"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return articleVO;
	}

	
	@Override
	public List<ArticleVO> findByMemberKey(Integer memberId) {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = HikariDataSource.getConnection();;
			pstmt = con.prepareStatement(GET_MEMBER_STMT);

			pstmt.setInt(1, memberId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// articleVo 也稱為 Domain objects
				ArticleVO articleVO = new ArticleVO();
				articleVO.setArticleId(rs.getInt("article_id"));
				articleVO.setArticleTitle(rs.getString("article_title"));
				articleVO.setArticleContent(rs.getString("article_content"));
				articleVO.setArticleDate(rs.getTimestamp("article_date"));
				articleVO.setArticleDateChange(rs.getTimestamp("article_date_change"));
				articleVO.setArticleImage(rs.getBytes("article_image"));
				articleVO.setMemberId(rs.getInt("member_id"));
				articleVO.setTourId(rs.getInt("tour_id"));
				list.add(articleVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	@Override
	public List<ArticleVO> getAll() {
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = HikariDataSource.getConnection();;
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				articleVO = new ArticleVO();
				articleVO.setArticleId(rs.getInt("article_id"));
				articleVO.setArticleTitle(rs.getString("article_title"));
				articleVO.setArticleContent(rs.getString("article_content"));
				articleVO.setArticleDate(rs.getTimestamp("article_date"));
				articleVO.setArticleDateChange(rs.getTimestamp("article_date_change"));
//				articleVO.setArticleImage(rs.getBytes("articleImage"));
				articleVO.setAdminId(rs.getInt("admin_id"));
				articleVO.setMemberId(rs.getInt("member_id"));
				articleVO.setTourId(rs.getInt("tour_id"));
				list.add(articleVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	
	public ArticleVO getImageByArticleId(Integer articleId) throws SQLException, ClassNotFoundException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArticleVO articleVO = null;
		
		try {
			con = HikariDataSource.getConnection();;
			pstmt = con.prepareStatement("select article_id, article_image from article where article_id = ?");
			pstmt.setInt(1, articleId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticleId(rs.getInt("article_id"));
				articleVO.setArticleImage(rs.getBytes("article_image"));
				}
			} finally {
		    if (rs != null) {
		        rs.close();
		    }
		    if (pstmt != null) {
		        pstmt.close();
		    }
		    if (con != null) {
		        con.close();
		    }
		}

		
		return articleVO;
		
	}


	public List<ArticleVO> findByWords(String words){
		List<ArticleVO> list = new ArrayList<ArticleVO>();
		ArticleVO articleVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = HikariDataSource.getConnection();;
			pstmt = con.prepareStatement(SEARCH);
			pstmt.setString(1, "%" + words + "%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				articleVO = new ArticleVO();
				articleVO.setArticleId(rs.getInt("article_id"));
				articleVO.setArticleTitle(rs.getString("article_title"));
				articleVO.setArticleContent(rs.getString("article_content"));
				articleVO.setArticleDate(rs.getTimestamp("article_date"));
				articleVO.setArticleDateChange(rs.getTimestamp("article_date_change"));
				articleVO.setArticleImage(rs.getBytes("article_image"));
				articleVO.setMemberId(rs.getInt("member_id"));
				articleVO.setTourId(rs.getInt("tour_id"));
				list.add(articleVO);
				
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
		}
		
	}
	
	

