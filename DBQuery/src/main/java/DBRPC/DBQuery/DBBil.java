/**    
 * 文件名：DBBil.java    
 *    
 * 版本信息：    
 * 日期：2018年3月18日    
 * Copyright 足下 Corporation 2018     
 * 版权所有    
 *    
 */
package DBRPC.DBQuery;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;
import DBRPC.DBAcess.DBAcessResult;
import DBRPC.DBModel.DataColumn;
import DBRPC.DBModel.ReturnResult;
import DBRPC.DBModel.SQLModel;
import DBRPC.DBModel.TableModel;
import Files.BufferFile;

/**    
 *     
 * 项目名称：DBQuery    
 * 类名称：DBBil    
 * 类描述：   执行SQL 
 * 创建人：jinyu    
 * 创建时间：2018年3月18日 上午12:17:00    
 * 修改人：jinyu    
 * 修改时间：2018年3月18日 上午12:17:00    
 * 修改备注：    
 * @version     
 *     
 */
public class DBBil {
  
  private DBAcessResult db=new DBAcessResult();
  
  /**
   * 
   * @Title: queryData   
   * @Description: 查询数据
   * @param param
   * @return      
   * ReturnResult      
   * @throws
   */
  public ReturnResult  queryData(SQLModel param)
  {
            ReturnResult result=new ReturnResult();
            List<Object> list=null;
            if(param.list==null||param.list.isEmpty())
            {
                 list=db.excuteQuery(param.sql, null);
            }
            else
            {
                list = db.excuteQuery(param.sql, param.list.toArray());
            }
            if(list==null)
            {
                result.error=db.getEror();
                result.error="fail";
            }
            result.rows=list;
            return result;
    
  }
  
  /**
   * 
   * @Title: queryTable   
   * @Description: 查询
   * @param param
   * @return      
   * ReturnResult      
   * @throws
   */
  public ReturnResult  queryTable(SQLModel param)
  {
            ReturnResult result=new ReturnResult();
            ResultSet rs=null;
            if(param.list==null||param.list.isEmpty())
            {
                 rs=db.executeQuerySql(param.sql);
            }
            else
            {
                rs = db.executeQueryRS(param.sql, param.list.toArray());
            }
            if(rs==null)
            {
                result.error=db.getEror();
                result.error="fail";
            }
            else
            {
                try
                {
                TableModel model=new TableModel();
                model.columns=new LinkedList<DataColumn>();
                model.rows=new LinkedList<Object>();
                ResultSetMetaData columns = rs.getMetaData();
                int size=columns.getColumnCount();
                //列
                for(int i=0;i<size;i++)
                {
                  DataColumn e=new DataColumn();
                  e.caption=columns.getColumnLabel(i);
                  e.name=columns.getColumnName(i);
                  e.sqlType=columns.getColumnType(i);
                  e.clsType=columns.getColumnClassName(i);
                  e.len=columns.getColumnDisplaySize(i);
                  model.tableName=columns.getTableName(i);
                   model.columns.add(e);
                }
                while(rs.next())
                {
                    for(int i=0;i<size;i++)
                    {
                        model.rows.add(rs.getObject(i));
                    }
                }
                result.rows=model;
                }
                catch(Exception ex)
                {
                    result.error=ex.getMessage();
                }
            }
            return result;
  }
  
  /**
   * 
   * @Title: queryXML   
   * @Description: 查询
   * @param param
   * @return      
   * ReturnResult      
   * @throws
   */
  public ReturnResult queryXML(SQLModel param)
  {
      String r="";
      if(param.list==null||param.list.isEmpty())
      {
           r=db.executeQueryXML(param.sql,null);
      }
      else
      {
          r = db.executeQueryXML(param.sql, param.list.toArray());
      }
      ReturnResult result=new ReturnResult();
      result.rows=r;
      if(r==null)
      {
          result.error=db.getEror();
          result.returnCode="fail";
      }
      return result;
  }
  
  /**
   * 
   * @Title: executeDML   
   * @Description: 执行SQL
   * @param param
   * @return      
   * ReturnResult      
   * @throws
   */
  public ReturnResult executeDML(SQLModel param)
  {
      int r=0;
      if(param.list==null||param.list.isEmpty())
      {
           r=db.executeDMLSql(param.sql);
      }
      else
      {
          r = db.executeUpdate(param.sql, param.list.toArray());
      }
      ReturnResult result=new ReturnResult();
      result.rows=r;
      if(r==0)
      {
          result.error=db.getEror();
          if(!result.error.isEmpty())
          {
             result.returnCode="fail";
          }
      }
      return result;
  }
  public ReturnResult executeBatch(SQLModel param)
  {
      int r=0;
      if(param.list!=null&&!param.list.isEmpty())
      {
           Object[][] list=new Object[param.list.size()][];
           Object[] obj= param.list.toArray();
           for(int i=0;i<obj.length;i++)
           {
               list[i]=(Object[]) param.list.get(i);
           }
           r = db.executeBatchSql(param.sql,list,param.commitNum);
      } 
      ReturnResult result=new ReturnResult();
      result.rows=r;
      if(r==0)
      {
          result.error=db.getEror();
          if(!result.error.isEmpty())
          {
             result.returnCode="fail";
          }
      }
      return result;
  }
  public ReturnResult executeBatchSQL(SQLModel param)
  {
      int r=0;
      if(param.sqls!=null)
      {
          db.executeBatchSql(param.sqls);
      } 
      ReturnResult result=new ReturnResult();
      result.rows=r;
      if(r==0)
      {
          result.error=db.getEror();
          if(!result.error.isEmpty())
          {
             result.returnCode="fail";
          }
      }
      return result;
  }
  public ReturnResult executeSigle(SQLModel param)
  {
      Object r=0;
      if(param.list==null)
      {
         r= db.executeQuerySingle(param.sql, null);
      } 
      else
      {
         r= db.executeQuerySingle(param.sql, param.list.toArray());
      }
      ReturnResult result=new ReturnResult();
      result.rows=r;
      if(r==null)
      {
          result.error=db.getEror();
          if(!result.error.isEmpty())
          {
             result.returnCode="fail";
          }
      }
      return result;
  }
  
  public void recSQLFile(SQLModel params)
  {
      String dir="SQLFile";
      File f=new File(dir);
      if(!f.exists())
      {
          f.mkdir();
      }
      //
      BufferFile writer=new BufferFile();
     byte[] tmp=new byte[params.list.size()];
     for(int i=0;i<tmp.length;i++)
     {
         tmp[i]=(Byte) params.list.get(i);
     }
      try {
        writer.writeFile(dir+"/"+params.sql, tmp);
    } catch (IOException e) {
          System.out.println(e.getMessage());
    }
  }
}
