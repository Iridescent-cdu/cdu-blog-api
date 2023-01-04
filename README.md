# Javaweb

## 1.CS和BS的异同点

1. CS：客户端服务器架构模式
   1. 优点：充分利用客户端机器的资源，减轻服务器的负荷（一部分安全要求不高的计算任务存储任务放在客户端执行，不需要把所有的计算和存储都在服务器端执行，从而减轻服务器端的压力，也能够减轻网络负荷）
   2. 缺点：需要安装，升级维护成本较高
2. BS：浏览器服务器架构模式
   1. 优点：客户端不需要安装，维护成本较低
   2. 缺点：所有的计算和存储任务在服务端，服务器负荷较重，在服务端计算完成之后把结果再传输给客户端，因此客户端和服务器端会进行频繁的数据通信，从而网络负荷较重

### 2.新版idea 2022.3.1创建Java Web项目

新版idea 移除了Web 模块的创建（没有找到），需要手动创建Web项目结构：

1. 新建web目录，创建WEB-INF/lib、WEB-INF/web.xml文件并写入web-app配置：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
            version="4.0">
   </web-app>
   ```

2. 创建展开型Web应用程序工件：添加模块源对应模块中的根模块，并选择右边可用元素，双击添加根模块的编译输出，最后为模块添加依赖

   1. 依赖可放入WEB-INF/lib目录下，方便移动
   2. 也可放入外部库中引用

3. 至此，工件中包含源代码文件和编译后的class文件两部分，工件中的内容即为我们添加的模块，模块中又需要添加依赖

## 3.servlet处理中文乱码问题

1. get方式目前不需要设置编码（基于tomcat8），在tomcat8之前，如果是get请求发送的中文数据，转码有点麻烦：

   ```java
   String fname = req.getParameter("fname")
   byte[] bytes = fname.getBytes("ISO-8859-1")
   fname = new String(bytes,"UTF-8")
   ```

2. post方式下，需要设置编码，防止中文乱码，并且值得注意的是，必须在所有的获取参数动作之前设置

   ```java
   req.setCharacterEncoding("UTF-8")
   ```

## 4.Session会话跟踪技术

1. HTTP无状态：服务器无法判断两次请求是同一个客户端发过来的，还是不同的的客户端发过来的

2. Session会话跟踪：

   1. 客户端第一次发请求给服务器，服务器获取session，获取不到，则通过set-cookie响应头的形式创建新的并返回给客户端

   2. 下次客户端给服务端发请求时，会自动带上cookie请求头给服务器，服务器就能获取到

      ```java
      HttpSession httpSession = req.getSession();
      ```

   3. 常用API：

      1. request.getSession()：获取当前的会话，没有则创建一个新的会话，接收一个Boolean参数，默认为true，当为false时，没有则返回null，不会创建新的session
      2. session.getId()：获取SessionID
      3. session.isNew()：判断当前session是否是新的
      4. session.getMaxInactiveInterval()：session的非激活间隔时长，默认1800秒，半个小时
      5. session.setMaxInactiveInterval()：设置session时效
      6. session.invalidate()：强制让session会话失效
      7. session.getLastAccessTime()：获取最后一次session访问时间

   4. session作用域：可以为session对象设置属性，同一个session对象设置后的属性可在全局所有组件servlet中获取到

      1. session.getAttribute()：获取同一个session对象的属性
      2. session.setAttribute()：设置同一个session对象的属性

## 5.客户端发送JSON格式的数据以及服务端的响应

1. 服务端获取参数值不再是req.getParameter()，而是使用BufferedReader字符流接收json字符串

```java
//1.由于请求体数据可能很大，所以servlet标准在设计api的时候要求我们通过输入流来读取
BufferedReader bufferedReader = req.getReader();
//2.创建StringBuffer对象来累加存储从请求体读取到的每一行
StringBuffer stringBuffer = new StringBuffer();
//3.声明临时变量
String str = null;
//4.循环读取
while ((str = bufferedReader.readLine()) != null) {
    stringBuffer.append(str);
}
//5.将stringBuffer转化成String字符串
str = stringBuffer.toString();
```

2. 使用Gson 包将JSON格式字符串转换为java对象

```java
//6.使用Gson 将JSON字符串转换为java对象 两种创建方式，GsonBuilder功能更加强大
// Gson gson =new GsonBuilder().create();
Gson gson = new Gson();
//7.将str转化为User类型的对象
//  gson.fromJson(str,User.class);
```

## 6.JDBCUtil封装和增删改查操作

1. 封装JDBCUtil工具类：提供两个静态方法，一个创建连接，一个关闭连接

   ```java
   public static Connection getConnection() throws Exception {
           //1.定义配置信息
           String driver = "com.mysql.cj.jdbc.Driver";
           String url = "jdbc:mysql://localhost:3306/cdu-blog?serverTimezone=GMT";
           String jdbcUser = "root";
           String jdbcPwd = "admin";
           //2.加载驱动
           Class.forName(driver);
           //3.获取连接对象
           Connection conn=DriverManager.getConnection(url, jdbcUser, jdbcPwd);
           return conn;
   }
   ```

   ```java
    public static void closeResource(Connection conn, PreparedStatement ps) {
           //1.关闭连接对象
           try {
               if (conn != null) {
                   conn.close();
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
           //2.关闭操作sql的对象
           try {
               if (ps != null) {
                   ps.close();
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
   ```

2. 使用PreparedStatement进行添加操作：需要使用数据库连接对象conn创建Statement和PreparedStatement对象，它们的区别如下：

   1. Statement用于执行静态SQL语句，在执行时，必须指定一个事先准备好的SQL语句（完整的SQL不包含占位符）
   2. PrepareStatement是预编译的SQL语句对象，sql语句被预编译并保存在对象中。被封装的sql语句代表某一类操作，语句中可以包含动态参数“?”，在执行时可以为“?”动态设置参数值
   3. 使用PrepareStatement对象执行sql时，sql被数据库进行解析和编译，然后被放到命令缓冲区，每当执行同一个PrepareStatement对象时，它就会被解析一次，但不会被再次编译。在缓冲区可以发现预编译的命令，并且可以重用
   4. PrepareStatement可以减少编译次数提高数据库性能

   ```java
   public void testUpdate() {
       try {
               //1.获取数据库的连接
       Connection conn = JDBCUtil.getConnection();
       //2.预编译sql语句，返回PreparedStatement的实例对象
       String sql = "update customers set name = ? where id = ?";    
       Statement statement = conn.createStatement();
       PreparedStatement ps = conn.prepareStatement(sql);  
       //3.填充占位符
       ps.setObject(1,"莫扎特");
       ps.setObject(2,18);
       //4.执行
       ps.execute();
       }catch(Exception e){
           e.printStackTrace();
       }finally{
            //5.资源的关闭
       JDBCUtil.closeResource(conn,ps);
       }
   }
   ```

3. PreparedStatement实现通用的增删改操作，不同执行语句区别如下：
   1. ResultSet executeQuery(String sql); 执行SQL查询，并返回ResultSet 对象
   2. int executeUpdate(String sql); 可执行增，删，改，返回执行受到影响的行数
   3. boolean execute(String sql); 可执行任何SQL语句，返回一个布尔值，表示是否返回ResultSet 

```java
public void update(String sql, Object... args) {
    try {
        //1.获取数据库的连接
        Connection conn = JDBCUtil.getConnection();
        //2.预编译sql语句，返回PreparedStatement的实例对象
        PreparedStatement ps = conn.prepareStatement(sql);
        //3.填充占位符
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i + 1, args[i]);
        }
        //4.执行
        ps.execute();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        //5.资源的关闭
        JDBCUtil.closeResource(conn, ps)
    }
}
```

