一、开发中使用控件及技术
    1、lombok
        （1）安装插件File-Setting-Plugins-Browse repositories,查找lombok,安装
        （2）引入依赖包
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    2、mybatis generator（安装插件即可，方法同lombok）
    3、mybaits
    4、springboot
    5、spring security
二、工具相关说明
    1、使用mysql8.0.15，jdk8,
    2、开发工具idea 2017版
三、数据库表创建说明。
    1、myuser用户表
    CREATE TABLE myuser
    (
      id       BIGINT AUTO_INCREMENT
        PRIMARY KEY,
      username VARCHAR(128) NULL,
      password VARCHAR(256) NULL,
      enabled  TINYINT      NULL,
      locked   TINYINT      NULL
    )
    2、角色表
    CREATE TABLE role
    (
      id     BIGINT AUTO_INCREMENT
        PRIMARY KEY,
      name   VARCHAR(255) NULL,
      nameZh VARCHAR(255) NULL
    )
    3、用户角色表
    CREATE TABLE user_role
    (
      user_id BIGINT NULL,
      role_id BIGINT NULL
    )
    4、学生表
    CREATE TABLE student
    (
      id         BIGINT AUTO_INCREMENT
        PRIMARY KEY,
      sno        VARCHAR(64)  NULL,
      name       VARCHAR(64)  NULL,
      sex        VARCHAR(2)   NULL,
      birthday   DATE         NULL,
      qq         VARCHAR(32)  NULL,
      phone      VARCHAR(128) NULL,
      banjiname  VARCHAR(128) NULL,
      createtime DATETIME     NULL
    )
    5、老师表
    CREATE TABLE teacher
    (
      id         BIGINT AUTO_INCREMENT
        PRIMARY KEY,
      tno        VARCHAR(128) NULL,
      name       VARCHAR(128) NULL,
      title      VARCHAR(256) NULL,
      entrytime  DATE         NULL,
      createtime DATETIME     NULL
    )
    6、课程表
    CREATE TABLE course
    (
      id         BIGINT AUTO_INCREMENT
        PRIMARY KEY,
      coursename VARCHAR(128) NULL,
      term       VARCHAR(128) NULL,
      banjiname  VARCHAR(128) NULL,
      teacherid  BIGINT       NULL,
      createtime DATE         NULL
    )
    7、成绩表
    CREATE TABLE academicrecord
    (
      a_id       BIGINT AUTO_INCREMENT
        PRIMARY KEY,
      courseid   BIGINT NULL,
      studentid  BIGINT NULL,
      score      INT    NULL,
      createtime DATE   NULL
    )
四、springboot+mybatis增删改查快速实现及其碰到的问题解决
    1、引入mybatis、mysql相关依赖包
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>2.0.0</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.15</version>
        <scope>runtime</scope>
    </dependency>
    2、建立Controller、Model、Mapper、Service、Config文件夹
        （1）Controller文件夹，存放Controller文件，web访问入口
        （2）Model文件夹，所有实体类文件存放位置
        （3）Mapper文件夹，数据库访问接口文件，Mybatis的Mapper接口文件（类型为.java），
        与资源文件中对应的.xml文件配对。
        （4）Service文件夹，操作数据库的实现类，通常与Mapper接口文件方法对应，自动装配
        Mapper接口，调用接口方法操作数据库，数据库操作由mybatis框架实现。
        （5）Config文件夹，配置文件存放位置，Springboot相关配置可使用代码实现，比如格式转换
        或者过滤器、拦截器等。
    3、使用idea的数据库页，连接mysql数据库
        （1）点开Database页签，点“+”号，选择Data Source-mysql
        （2）在Data Source and Drivers对话框中，选择“扳手”符号，选择class类型、jdbc驱动
        并且连接参数：jdbc:mysql://127.0.0.1:3306/tree?useSSL=true&serverTimezone=GMT
        驱动：com.mysql.cj.jdbc.Driver，设置用户名、密码（本文中数据库名为tree）.
        （3）测试相关连接
    4、配置application.yml中数据库访问相关参数
        spring:
          datasource:
            url: jdbc:mysql://127.0.0.1:3306/tree?useSSL=true&serverTimezone=GMT
            username: root
            password: root
            driver-class-name: com.mysql.cj.jdbc.Driver
        #配置mybatis相关参数
        mybatis:
          mapper-locations: classpath:mapper/*Mapper.xml
          type-aliases-package: com.study.scoremanage.Model
        #控制台输出mybatis操作数据库的sql语句
        logging:
          level:
            com.study.scoremanage.Mapper: debug
    5、在Database页创建数据库表，右键点击数据库名，选择new-table,设置相关字段，创建表。
    6、右键点击需要增删改查的表，选择“Generate MyBatis Code”,设定Model文件夹位置Model，
       dao文件夹位置Mapper并修改后缀Dao为Mapper，xml文件夹位置mapper，适当修改options即可
       自动生成对应的model实体类、mapper接口、mapper配置文件。
    7、根据需要调整model.java、***Mapper.java、***Mapper.xml，完成增删改查功能。
        （1）student、teacher、course、academicrecord四张表存在相互关系，首先要厘清相互关系。
        （2）数据表字段与Model实体的设计不是直接对应，对于不存在关联关系的model实体，二者一致，
        对于存在关联关系的实体二者不完全一致，如选择student通过studentid关联出其成绩记录，则
        model设计如下
        @Data
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(value = { "handler" })
        public class Student implements Serializable {
            private Long id;
            private String sno; //学号
            private String name; //姓名
            private String sex; //性别
            private Date birthday; //出生日期
            private String qq; //qq号
            private String phone;
            private String banjiname;//班级名称
            private List<Academicrecord> academicrecords;
            private Date createtime;
            private static final long serialVersionUID = 1L;
        }
        （3）设计关联映射时，避免出现循环映射，比如student通过studentid可以映射出对应的成绩，即
        与academicrecord表间的关联关系，如果academicrecord通过studentid再关联出student，此时就会
        出现循环映射，导致程序执行错误，解决办法就是在设计关联映射时选择一对一、一对多使用最频繁
        的映射方向，其反向不在model设计中体系映射关系。其余几个model设计如下：
        @Data
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(value = { "handler" })
        public class Course implements Serializable {
            private Long cou_id;
            private String coursename;
            private String term;
            private String banjiname;
            private Teacher teacher;
            private List<Academicrecord> academicrecords;
            private Date createtime;
            private static final long serialVersionUID = 1L;
        }
        ///////////////
        @Data
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(value = { "handler" })
        public class Teacher implements Serializable {
            private Long tea_id;
            private String tno;
            private String name;
            private String title;
            private Date entrytime;
            private Date createtime;
            private static final long serialVersionUID = 1L;
        }
        //////////////////////////
        @Data
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(value = { "handler" })
        public class Academicrecord implements Serializable {
            private Long aca_id;
            private String courseid;
            private String studentid;
            private Integer score;
            private Date createtime;
            private static final long serialVersionUID = 1L;
        }
        /////////此处选择，student、course关联映射academicrecord表。即可以直接从
        ////////student的实体类中读取成绩，可以从course实体中读取成绩。从academicrecord
        ///////实体中只能直接读取studentid和courseid。
    8、关联表如何实现，
        （1）首先理解MyBatis Generator控件自动生成的mapper接口及mapper配置文件
        结构。有如下几个特点：一是有主键的表，产生的mapper接口有规律，通常包括：
        按主键删除deleteByPrimaryKey、插入insert、动态插入insertSelective、
        按主键查找selectByPrimaryKey、按主键动态更新updateByPrimaryKeySelective
        按主键更新updateByPrimaryKey等几个常规接口；二是mapper接口方法与mapper配置文件
        中bean的id存在一一对应关系；三是mapper配置文件包括<resultMap id="BaseResultMap"、
        <sql id="Base_Column_List">及带sql语法特点的bean三大部分组成。
        （2）根据实际情况增加或删除部分mapper接口，主要包括关联外键的查询方法、相关服务
        需要的数据库访问接口。如下例：
            @Repository
            @Mapper
            public interface RoleMapper {
                int deleteByPrimaryKey(Long id);
                int insert(Role record);
                int insertSelective(Role record);
                Role selectByPrimaryKey(Long id);
                int updateByPrimaryKeySelective(Role record);
                int updateByPrimaryKey(Role record);
                //以下为手动添加内容
                List<Role> selectRole(Role role);
                List<Role> selectRolesByUserId(Long id);
                List<Role> selectRolesByRoleName(String rolename);
            }
        （3）将手动添加的接口方法在mapper配置文件中增加对应的bean。举例如下：
            <!--以下为手动添加内容-->
              <select id="selectRole" parameterType="com.study.scoremanage.Model.Role" resultMap="BaseResultMap">
                <!--省略相关内容-->
              </select>
              <select id="selectRolesByUserId" parameterType="java.lang.Long" resultMap="BaseResultMap">
                <!--省略相关内容-->
              </select>
              <select id="selectRolesByRoleName" parameterType="java.lang.String" resultMap="BaseResultMap">
                <!--省略相关内容-->
              </select>
        (4)修改resultMap相关内容，重点关注一对一映射和一对多映射，分别举例如下：
        <resultMap id="BaseResultMap" type="com.study.scoremanage.Model.Course">
            <id column="id" property="cou_id"/>
            <result column="coursename" property="coursename"/>
            <result column="term" property="term"/>
            <result column="banjiname" property="banjiname"/>
            <result column="createtime" jdbcType="DATE" property="createtime"/>
            <association property="teacher" javaType="com.study.scoremanage.Model.Teacher"
                         column="teacherid"
                         select="com.study.scoremanage.Mapper.TeacherMapper.selectByPrimaryKey">
                <id column="id" jdbcType="BIGINT" property="tea_id" />
                <result column="tno" jdbcType="VARCHAR" property="tno" />
                <result column="name" jdbcType="VARCHAR" property="name" />
                <result column="title" jdbcType="VARCHAR" property="title" />
                <result column="entrytime" jdbcType="DATE" property="entrytime" />
                <result column="createtime" jdbcType="TIMESTAMP" property="createtime" />
            </association>
            <collection property="academicrecords" javaType="ArrayList"
                        column="cou_id"
                        ofType="com.study.scoremanage.Model.Academicrecord"
                        select="com.study.scoremanage.Mapper.AcademicrecordMapper.selectByCourseId"
                        fetchType="lazy">
            </collection>
        </resultMap>
        （5）在处理关联表的映射时注意resultMap有格式要求，特别是顺序要求，result在前面，如果有
        在接下来依次为association（一对一映射）、collection（一对多映射）。
    9、增加对应的service，其中service一般与mapper接口方法对应，特殊情况下
        也可在接口方法基础上对参数进行操作后，传入接口中。举例如下：
        @Service
        public class StudentService {
            final private StudentMapper studentMapper;
            @Autowired
            public StudentService(StudentMapper studentMapper)
            {
                this.studentMapper = studentMapper;
            }
            public int AddStudent(Student student)
            {
                //添加学生信息
                return studentMapper.insertStudent(student);
            }
            public int EditStudentById(Student student)
            {
                //修改学生信息
                return studentMapper.updateStudentById(student);
            }

            public int EditStudentById(Long id,Student student)
            {
                //修改学生信息
                student.setId(id);
                return studentMapper.updateStudentById(student);
            }
            public int  DeleteStudent(Student student)
            {
                //删除学生信息
                return studentMapper.deleteStudent(student);
            }
            public int  DeleteStudentById(Long id)
            {
                //删除学生信息
                return studentMapper.deleteStudentById(id);
            }
            public List<Student> GetStudent(Student student)
            {
                //读取学生信息
                return studentMapper.selectStudent(student);
            }
            public Student GetStudentById(Long id)
            {
                //读取学生信息
                return studentMapper.selectByPrimaryKey(id);
            }
        }
    10、增加对应点额controller，按照restful标准设计controller，举例如下：
        @Slf4j
        @RestController
        //@RequestMapping("/users")
        public class StudentController {
            final private StudentService studentService;

            @Autowired
            public StudentController(StudentService studentService)
            {
                this.studentService = studentService;
            }

            //获取学生信息，字段精确查找，无参数为查询全部信息
            @GetMapping("/students")
            public List<Student> getStudent(Student student){
                log.info("测试！");
                log.info(student.toString());
                return studentService.GetStudent(student);
            }

            //获取学生信息，路径id为准
            @GetMapping("/students/{id}")
            public Student getStudentById(@PathVariable("id") Long id){
                log.info("测试！");
                return studentService.GetStudentById(id);
            }

            //增加学生信息
            @PostMapping("/students")
            public int AddStudent(@RequestBody Student student){
                log.info(student.toString());
                return studentService.AddStudent(student);
            }

            //修改学生信息，以ID为准
            @PutMapping("/students")
            public int EditStudentById(@RequestBody Student student)
            {
                log.info("put测试！");
                log.info(student.toString());
                return studentService.EditStudentById(student);
            }

            //修改学生信息，路径中ID为准
            @PutMapping("/students/{id}")
            public int EditStudentById(@PathVariable("id") Long id,@RequestBody Student student)
            {
                log.info("put测试！");
                log.info(student.toString());
                return studentService.EditStudentById(id,student);
            }

            //删除学生信息，字段精确查找为准
            @DeleteMapping("/students")
            public int DeleteStudent(@RequestBody Student student)
            {
                return studentService.DeleteStudent(student);
            }

            //删除学生信息，路径id为准
            @DeleteMapping("/students/{id}")
            public int DeleteStudentById(@PathVariable("id") Long id)
            {
                return studentService.DeleteStudentById(id);
            }
        //
        }
    11、安装postman，设置相关测试collection。
        （1）new collection设置相关参数
        （2）Add request设置，添加各资源的增删改查模拟测试。
        （3）查询，使用Get模式，输入网址，不需添加任何参数（启用spring security除外）
        直接可点“send”测试。
        （4）增加记录，使用post模式，设置body、raw,输入json（可复制查询结果后修改），
        格式修改为json,点“send”测试。
        （5）修改记录，使用put模式，删除记录使用delete模式。
五、启用spring security进行登录验证。
    1、引入spirng security依赖包。
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
    2、创建数据表：myuser、role、user_role表
        CREATE TABLE myuser
        (
          id       BIGINT AUTO_INCREMENT
            PRIMARY KEY,
          username VARCHAR(128) NULL,
          password VARCHAR(256) NULL,
          enabled  TINYINT      NULL,
          locked   TINYINT      NULL
        )
        CREATE TABLE role
        (
          id     BIGINT AUTO_INCREMENT
            PRIMARY KEY,
          name   VARCHAR(255) NULL,
          nameZh VARCHAR(255) NULL
        )
        CREATE TABLE user_role
        (
          user_id BIGINT NULL,
          role_id BIGINT NULL
        )
    3、使用mybatis generator插件生成myuser、role、user_role三张表的增删改查
     的model、mapper以及mapper配置文件。
    4、综合考虑三张表对应的model的关联关系，设定以myuser关联role为关联方向
     修改myuser的model：
        @Slf4j
        @Data
        public class MyUser implements UserDetails{
            private Long id;
            private String username;
            private String password;
            private Boolean enabled;
            private Boolean locked;
            private List<Role> roles;
        }
    5、spring security中使用UserDetails作为接口存放用户信息，
     暂时不考虑相关内容，上述文件编译时报错，需要修改，添加部分
     接口实现，结果如下：
        @Slf4j
        @Data
        public class MyUser implements UserDetails{
            private Long id;
            private String username;
            private String password;
            private Boolean enabled;
            private Boolean locked;
            private List<Role> roles;

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for(Role role:roles){
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
                    log.info("用户角色权限："+role.getName());
                }
                return authorities;
            }
            @Override
            public boolean isAccountNonExpired() {
                return true;
            }
            @Override
            public boolean isAccountNonLocked() {
                if(locked == null)
                    return true;
                return !locked;
            }
            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }
            @Override
            public boolean isEnabled() {
                if(enabled == null)
                    return false;
                return enabled;
            }
            public Long getId() {
                return id;
            }
            @Override
            public String getUsername() {
                return username;
            }
            @Override
            public String getPassword() {
                return password;
            }
        }
     6、修改相关的Mapper及其配置文件，配置文件关键代码如下：
        <!--  MyUserMapper.xml关键代码   -->
        <resultMap id="BaseResultMap" type="com.study.scoremanage.Model.MyUser">
            <id column="id" jdbcType="BIGINT" property="id" />
            <result column="username" jdbcType="VARCHAR" property="username" />
            <result column="password" jdbcType="VARCHAR" property="password" />
            <result column="enabled" jdbcType="TINYINT" property="enabled" />
            <result column="locked" jdbcType="TINYINT" property="locked" />
            <collection property="roles" javaType="ArrayList"
                        column="id"
                        ofType="com.study.scoremanage.Model.Role"
                        select="com.study.scoremanage.Mapper.RoleMapper.selectRolesByUserId"
                        fetchType="lazy">
            </collection>
        </resultMap>

        <!--  RoleMapper.xml关键代码   -->
        <select id="selectRolesByUserId" parameterType="java.lang.Long" resultMap="BaseResultMap">
            select
            *
            from role r,user_role ur
            where r.id = ur.role_id and ur.user_id = #{id,jdbcType=BIGINT}
        </select>
     7、程序运行报错，根据报错结果，使用百度在线翻译，最后定位于MyUser出错，
        因为UserDetails接口的部分方法与@Data的部分实现方法冲突导致，此时需要
        将@Data更改为其他组合，并显示设定部分getter，结果如下：
        @Slf4j
        @Builder
        @Setter
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(value = { "handler" })
        public class MyUser implements UserDetails{
            private Long id;
            private String username;
            private String password;
            private Boolean enabled;
            private Boolean locked;
            private List<Role> roles;
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for(Role role:roles){
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
                    log.info("用户角色权限："+role.getName());
                }
                return authorities;
            }
            @Override
            public boolean isAccountNonExpired() {
                return true;
            }
            @Override
            public boolean isAccountNonLocked() {
                if(locked == null)
                    return true;
                return !locked;
            }
            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }
            @Override
            public boolean isEnabled() {
                if(enabled == null)
                    return false;
                return enabled;
            }
            public Long getId() {
                return id;
            }
            @Override
            public String getUsername() {
                return username;
            }
            @Override
            public String getPassword() {
                return password;
            }

            public Boolean getLocked() {
                return locked;
            }
            public List<Role> getRoles() {
                return roles;
            }
        }
     8、创建config配置程序，密码验证前进行了加密，方法二可用于关闭用户验证，直接执行相关web访问，
     代码如下：
        @Slf4j
        @Configuration
        public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
            final private MyUserService myUserService;
            @Autowired
            public WebSecurityConfig(MyUserService myUserService)
            {
                this.myUserService = myUserService;
            }
            @Bean
            PasswordEncoder passwordEncoder()
            {
                return new BCryptPasswordEncoder();
            }
            @Override
            protected void configure(AuthenticationManagerBuilder auth) throws Exception
            {
                auth.userDetailsService(myUserService);
            }
            @Override
            protected void configure(HttpSecurity http) throws Exception
            {
                log.info(http.authorizeRequests().toString());
                http.authorizeRequests()
                        //"/guest/**"接口允许所有人访问，包括未登录的人
                        .antMatchers("/myusers/**").permitAll()
                        //"/students/**"接口允许只能被拥有admin角色的用户访问
                        .antMatchers("/students/**").hasRole("admin")
                        //"/teachers/**"接口允许只能被拥有admin角色的用户访问
                        .antMatchers("/teachers/**").hasRole("admin")
                        //允许登录用户访问。
                        .anyRequest().authenticated()
                        .and()
                        .formLogin()
                        //登录页面login
                        .loginProcessingUrl("/login").permitAll()
                        .and()
                        //跨站请求伪造不可用
                        .csrf().disable();
                //方法二：关闭验证,追加http.csrf().disable()
        //        http.authorizeRequests().anyRequest().permitAll().and().logout().permitAll();
        //        http.csrf().disable();
            }
        }
     9、MyUserService代码有两个要点，一是插入用户记录时需加密后保存到数据库
     二是验证时输入的密码需要加密，二者加密使用同一算法，如下：
         @Slf4j
         @Service
         public class MyUserService implements UserDetailsService {
             final private MyUserMapper myUserMapper;
             @Autowired
             public MyUserService(MyUserMapper myUserMapper)
             {
                 this.myUserMapper = myUserMapper;
             }

             public int deleteByPrimaryKey(Long id)
             {
                 return myUserMapper.deleteByPrimaryKey(id);
             }

             public int insert(MyUser record)
             {
                 log.info("插入记录："+record);
                 //密码加密
                 BCryptPasswordEncoder encoding = new BCryptPasswordEncoder();
                 record.setPassword(encoding.encode(record.getPassword()));
                 log.info("密码加密后结果："+record);
                 return myUserMapper.insert(record);
             }

             public int insertSelective(MyUser record)
             {
                 log.info("插入记录："+record);
                 //密码加密
                 BCryptPasswordEncoder encoding = new BCryptPasswordEncoder();
                 record.setPassword(encoding.encode(record.getPassword()));
                 log.info("密码加密后结果："+record);
                 return myUserMapper.insertSelective(record);
                 //上述添加只添加myuser表中相关属性，未处理record中关于role相关设置。如需完善需添加相关内容

             }

             public MyUser selectByPrimaryKey(Long id)
             {
                 return myUserMapper.selectByPrimaryKey(id);
             }

             public int updateByPrimaryKeySelective(MyUser record)
             {
                 return myUserMapper.updateByPrimaryKeySelective(record);
             }

             public int updateByPrimaryKey(MyUser record)
             {
                 return myUserMapper.updateByPrimaryKey(record);
             }

             //以下为手动添加
             public List<MyUser> selectMyUser(MyUser myUser)
             {
                 log.info("运行至service"+myUser);
                 return myUserMapper.selectMyUser(myUser);
             }

             public int deleteMyUser(MyUser myUser)
             {
                 return myUserMapper.deleteMyUser(myUser);
             }


             //覆盖方法
             @Override
             public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                 log.info("开始登录测试。");
                 MyUser myUser = myUserMapper.loadMyUserByUsername(username);
         //        log.info("测试登录验证。");
                 if(myUser == null)
                 {
                     throw new UsernameNotFoundException("账户不存在！");
                 }else
                 {
                     log.info("账户存在"+myUser.toString());
                 }
                 //方法一：隐藏实现了设置roles，重点在于设置roles时需加"ROLE_"前缀。
                 // myUser.setRoles(myUser.getRoles());
                 return myUser;

                 //修改重点。方法二：显示实现接口UserDetails的类，并设置roles为myUser.getAuthorities(),
         //        UserDetails	user = new User(myUser.getUsername(), myUser.getPassword(), true, true, true, true,myUser.getAuthorities());
         //        return user;
             }
         }
     10、关闭密码验证，进行用户、权限管理，即操作myuser、user_role、role表。
     11、使用浏览器访问student，验证spring security设置的有效性。
六、界面设计
    1、login.html页面设计，使用dreamweaver进行效果设计。
    2、引入thymeleaf相关依赖
    3、在传统WEB工程开发时，路径的处理操作是有点麻烦的。SpringBoot中为我们简化了路径的处理。
    在页面中可以通过“@{路径}”来引用。页面之间的跳转也能通过@{}来实现。
    4、模版开发框架里面是不提倡使用内置对象的，但是很多情况下依然需要使用内置对象进行处理，
     所以下面来看下如何在页面中使用JSP内置对象。
     （1）在控制器里面增加一个方法，这个方法将采用内置对象的形式传递属性。
         @RequestMapping(value = "/inner", method = RequestMethod.GET)
         public String inner(HttpServletRequest request, Model model) {
             request.setAttribute("requestMessage", "springboot-request");
             request.getSession().setAttribute("sessionMessage", "springboot-session");
             request.getServletContext().setAttribute("applicationMessage",
                     "springboot-application");
             model.addAttribute("url", "www.baidu.cn");
             request.setAttribute("url2",
                     "<span style='color:red'>www.baidu.cn</span>");
             return "show_inner";
         }
     （2）在页面之中如果要想访问不同属性范围中的内容，则可以采用如下的做法完成
        <!DOCTYPE HTML>
        <html xmlns:th="http://www.thymeleaf.org">
        <head>
            <title>SpringBoot模版渲染</title>
            <script type="text/javascript" th:src="@{/js/main.js}"></script>
            <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
        </head>
        <body>
            <p th:text="${#httpServletRequest.getRemoteAddr()}"/>
            <p th:text="${#httpServletRequest.getAttribute('requestMessage')}"/>
            <p th:text="${#httpSession.getId()}"/>
            <p th:text="${#httpServletRequest.getServletContext().getRealPath('/')}"/>
            <hr/>
            <p th:text="'requestMessage = ' + ${requestMessage}"/>
            <p th:text="'sessionMessage = ' + ${session.sessionMessage}"/>
            <p th:text="'applicationMessage = ' + ${application.applicationMessage}"/>
        </body>
        </html>
     5、快速开发做法
         (1)将静态网页模板，复制到resource/static目录下
         (2)导入例子路径：“bootstrap网站后台ui管理系统免费下载\dgfp_43_hs\”。
         (3)将部分html文件复制到templates目录下，可另外创建目录，此时无法通过直接路径访问
            需通过controller跳转，注意controller无法跳转到静态页面，即无法直接访问static
            路径下的html文件。
         (4)文件夹路径中存在多个bootstrap导致出现莫名其妙错误，后将父路径修改为bootstrap3问题消除
            另外templates下的html文件引用静态页面时需使用绝对路径。
         (5)自定义login页面时注意三点：一是login页面存放在templates目录下；二是login页面不能引用
            static目录下的静态资源，否则静态资源未登录情况下回自动跳转；三是WebSecurityConfig中
            注册登录页面；四是controller中创建跳转/login。其中WebSecurityConfig关键代码如下
            @Slf4j
            @Configuration
            public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
                final private MyUserService myUserService;
                private final MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;
                @Autowired
                public WebSecurityConfig(MyUserService myUserService,MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler)
                {
                    this.myUserService = myUserService;
                    this.myAuthenctiationSuccessHandler = myAuthenctiationSuccessHandler;
                }
                @Bean
                PasswordEncoder passwordEncoder()
                {
                    return new BCryptPasswordEncoder();
                }

                @Override
                protected void configure(AuthenticationManagerBuilder auth) throws Exception
                {
                    auth.userDetailsService(myUserService);
                }

                @Override
                public void configure(WebSecurity web) throws Exception {
                    //解决静态资源被拦截的问题
                    web.ignoring().antMatchers("/bootstrap3/images/**","/bootstrap3/lib/**","/bootstrap3/javascripts/**","/bootstrap3/stylesheets/**");
                }

                @Override
                protected void configure(HttpSecurity http) throws Exception
                {
                    log.info(http.authorizeRequests().toString());

                    http.cors().and().csrf().disable();
                    http
                            .authorizeRequests().antMatchers("/**/*.css","/bootstrap3/**","/bootstrap4/**").permitAll()
                            //使用form表单post方式进行登录w
                            .and().formLogin()
                            //登录页面为自定义的登录页面
                            .loginPage("/login")
                            //设置登录成功后跳转到登录前页面
                            .successHandler(new AuthenticationSuccessHandler() {
                                @Override
                                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                    Authentication authentication) throws IOException, ServletException {
                                    response.setContentType("application/json;charset=utf-8");
                                    RequestCache cache = new HttpSessionRequestCache();
                                    SavedRequest savedRequest = cache.getRequest(request, response);
                                    String url = savedRequest.getRedirectUrl();
                                    response.sendRedirect(url);
                                }
                            })
                            .permitAll()
                            .and()
                            //其他的需要授权后访问
                            .authorizeRequests().anyRequest().authenticated();
            //
            //        //session管理,失效后跳转
            //        http.sessionManagement().invalidSessionUrl("/login");
            //        //单用户登录，如果有一个登录了，同一个用户在其他地方登录将前一个剔除下线
            //        //http.sessionManagement().maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy());
            //        //单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
            //        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
            //        //退出时情况cookies
            //        http.logout().deleteCookies("JESSIONID");
            //        //解决中文乱码问题
            //        CharacterEncodingFilter filter = new CharacterEncodingFilter();
            //        filter.setEncoding("UTF-8"); filter.setForceEncoding(true);
                }
            }
七、bootstrap局部更新的尝试
    1、bootstrap支持使用iframe
    2、使用thymeleaf的include引入公共页面功能
        （1）首先建立一个公共页面common_header.html
        （2）在这个页面使用了一个标签fragment=“common_js_css”，可以理解是在
         这个页面中某个代码块的ID,此页面可以写多个fragment。内容如下：
         <!DOCTYPE HTML>
         <html xmlns:th="http://www.thymeleaf.org">
         <head>
         <title>公共页面</title>
         <meta name="viewport" content="width=device-width, initial-scale=1">
         <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
         </head>
         <body>
            <div th:fragment=”common_js_css“>
            <script type=”text/javascript” th:src=”@{/plugins/jquery/jquery-3.0.2.js}”>
            </script>
            <link href=”/css/style.css” rel=”stylesheet” type=”text/css”>
            </div>
        </body>
        </html>
        （3）在具体需要使用的页面，比如index.htm中这样使用，在需要的地方直接引入：
        <span th:replace=”common_header::common_js_css“></span>
        其中：common_header是需要引入页面的html名称，后面common_js_css是需要引入的代码块的ID,
        如果2个页面不在同一个页面，需要加路径。
        （4）replace，类似功能的还有下面2个,有所区别：
               th:insert：保留自己的主标签，保留th:fragment的主标签。
               th:replace：不要自己的主标签，保留th:fragment的主标签。
               th:include：保留自己的主标签，不要th:fragment的主标签。（官方3.0后不推荐）









