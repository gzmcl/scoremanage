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
        （1）




