package com.john.gestoralumnos.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.john.gestoralumnos.UserNotFoundException;
import com.john.gestoralumnos.student.StudentItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Gestor alumnos</h1>
 * <h2>SQLite, Bases de Datos Helper </h2>
 * <p>Esta clase es la base de nuestra base de datos. Aqui es donde especificamos como se
 * hara las llamadas para consultar y CRUD (Create, Retrieve, Update, Delete).</p>
 *
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @Version 7
 */

public class SQLite_Helper {
//notes: https://stackoverflow.com/questions/7905859/is-there-an-auto-increment-in-sqlite


    // Nombre del fichero en el que se guardará la base de datos
    private static final String DATABASE_NAME = "gestorAlumnos.db";
    /* Versión de la base de datos, indicada por el programador. En el
     caso de que introduzcamos algún cambio deberíamos modificar este número de versión,
    con lo que el MiOpenHelper determinará qué hacer */
    private static final int DATABASE_VERSION = 7;

    // Nuestras tablas
    private static final String TABLE_NAME_USER = "usuario";
    private static final String TABLE_NAME_SUBJECT = "asignatura";
    private static final String TABLE_NAME_STUDENT = "alumno";
    private static final String TABLE_NAME_GRADES = "notas";
    private static final String TABLE_NAME_STUDENT_SUBJECT = "alumno_asignatura";
    private static final String TABLE_NAME_ATTENDENCE = "faltas";


    // Las tablas y sus columnas
    private static final String[] COLUMNAS_USER = {"usuario", "contrasena", "nombre", "apellidos", "email"};
    private static final String[] COLUMNAS_SUBJECT = {"id", "asignatura"};
    private static final String[] COLUMNAS_STUDENT = {"id", "nombre", "apellidos", "email", "foto"};
    private static final String[] COLUMNAS_GRADES = {"id_estudiante", "id_asignatura", "primeraevaluacion", "segundaevaluacion"};
    private static final String[] COLUMNAS_STUDENT_SUBJECT = {"id_alumno", "id_asig"};
    private static final String[] COLUMNAS_ATTENDENCE = {"id", "id_estudiante", "id_asignatura", "fecha"};

    /**
     *  Incluimos el código SQL como constantes
     */


    /****** INSERT ****/
    private static final String INSERT_USER = "insert into " + TABLE_NAME_USER +
            "(" + COLUMNAS_USER[0] + "," + COLUMNAS_USER[1] + "," + COLUMNAS_USER[2] + "," + COLUMNAS_USER[3] + "," + COLUMNAS_USER[4] + ") values (?,?,?,?,?)";

    private static final String INSERT_SUBJECT = "insert into " + TABLE_NAME_SUBJECT +
            "(" + COLUMNAS_SUBJECT[1] + ") values (?)";
    private static final String INSERT_STUDENT = "insert into " + TABLE_NAME_STUDENT +
            "(" + COLUMNAS_STUDENT[1] + "," + COLUMNAS_STUDENT[2] + "," + COLUMNAS_STUDENT[3] + "," + COLUMNAS_STUDENT[4] + ") values (?,?,?,?)";
    private static final String INSERT_GRADES = "insert into " + TABLE_NAME_GRADES +
            "(" + COLUMNAS_GRADES[1] + "," + COLUMNAS_GRADES[2] + "," + COLUMNAS_GRADES[3] + ") values (?,?,?)";
    private static final String INSERT_STUDENT_SUBJECT = "insert into " + TABLE_NAME_STUDENT_SUBJECT +
            "(" + COLUMNAS_STUDENT_SUBJECT[0] + "," + COLUMNAS_STUDENT_SUBJECT[1] + ") values (?,?)";
    private static final String INSERT_ATTENDENCE = "insert into " + TABLE_NAME_ATTENDENCE +
            "(" + COLUMNAS_ATTENDENCE[1] + "," + COLUMNAS_ATTENDENCE[2] + "," + COLUMNAS_ATTENDENCE[3] + ") values (?,?,?)";

    /****** UPDATE ****/
    private static final String UPDATE_USER = "update " + TABLE_NAME_USER + " set " +
            COLUMNAS_USER[0] + "= ?," + COLUMNAS_USER[1] + "= ?," + COLUMNAS_USER[2] + "= ?," + COLUMNAS_USER[3] + "= ?," + COLUMNAS_USER[4] + "= ? where " + COLUMNAS_USER[0] + "=?";
    private static final String UPDATE_SUBJECT = "update " + TABLE_NAME_SUBJECT + " set " +
            COLUMNAS_SUBJECT[1] + "= ? where " + COLUMNAS_SUBJECT[0] + "=?";

    private static final String UPDATE_STUDENT = "update " + TABLE_NAME_STUDENT + " set " +
            COLUMNAS_STUDENT[1] + "= ?," + COLUMNAS_STUDENT[2] + "= ?," + COLUMNAS_STUDENT[3] + "= ?," + COLUMNAS_STUDENT[4] + "= ? where " + COLUMNAS_STUDENT[0] + "=?";

    private static final String UPDATE_GRADES = "update " + TABLE_NAME_GRADES + " set " +
            COLUMNAS_GRADES[2] + "= ?," + COLUMNAS_GRADES[3] + "= ? where " + COLUMNAS_GRADES[0] + "=? and " + COLUMNAS_GRADES[1] + "=?";

    private static final String UPDATE_STUDENT_SUBJECT = "update " + TABLE_NAME_STUDENT_SUBJECT + "set " +
            COLUMNAS_STUDENT_SUBJECT[0] + "= ?," + COLUMNAS_STUDENT_SUBJECT[1] + "=? where " + COLUMNAS_STUDENT_SUBJECT[0] + "=? and " + COLUMNAS_STUDENT_SUBJECT[1] + " =?";

    private static final String UPDATE_ATTENDENCE = "update " + TABLE_NAME_ATTENDENCE + " set " +
            COLUMNAS_ATTENDENCE[1] + "= ?," + COLUMNAS_ATTENDENCE[2] + "= ?," + COLUMNAS_ATTENDENCE[3] + "= ? where " + COLUMNAS_ATTENDENCE[0] + "= ?";

    /****** DELETE *****/
    private static final String DELETE_USER = "delete from " + TABLE_NAME_USER + " where "
            + COLUMNAS_USER[0] + "=?";
    private static final String DELETE_SUBJECT = "delete from " + TABLE_NAME_SUBJECT + " where "
            + COLUMNAS_SUBJECT[0] + "=?";
    private static final String DELETE_STUDENT = "delete from " + TABLE_NAME_STUDENT + " where "
            + COLUMNAS_STUDENT[0] + "=?";
    private static final String DELETE_GRADES = "delete from " + TABLE_NAME_GRADES + " where "
            + COLUMNAS_GRADES[0] + "=? and " + COLUMNAS_GRADES[1] + "=?";
    private static final String DELETE_STUDENT_SUBJECT = "delete from " + TABLE_NAME_STUDENT_SUBJECT + " where "
            + COLUMNAS_STUDENT_SUBJECT[0] + "=? and " + COLUMNAS_STUDENT_SUBJECT[1] + " =?";
    private static final String DELETE_ATTENDENCE = "delete from " + TABLE_NAME_ATTENDENCE + " where "
            + COLUMNAS_ATTENDENCE[0] + "= ?";

    /***** SELECT *****/
    private static final String SELECT_USER = "select * from " + TABLE_NAME_USER + " where " + COLUMNAS_USER[0] + " =?; ";
    private static final String SELECT_ASIGNATURA = "select * from " + TABLE_NAME_SUBJECT + " where " + COLUMNAS_SUBJECT[0] + " =?; ";

/**
 * <p>Creando nuestra base de datos</p>
 */
    /*****DATABASE *****/
    private static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_NAME_USER +
                    "(" + COLUMNAS_USER[0] + " TEXT PRIMARY KEY,"   //usuario
                    + COLUMNAS_USER[1] + " TEXT,"               //contrasena
                    + COLUMNAS_USER[2] + " TEXT,"               //nombre
                    + COLUMNAS_USER[3] + " TEXT,"               //apellidos
                    + COLUMNAS_USER[4] + " TEXT );";             //email
    private static final String CREATE_TABLE_SUBJECT =
            "CREATE TABLE " + TABLE_NAME_SUBJECT +
                    "(" + COLUMNAS_SUBJECT[0] + " INTEGER PRIMARY KEY," //id
                    + COLUMNAS_SUBJECT[1] + " TEXT);";           //nombre asignatura

    private static final String CREATE_TABLE_STUDENT =
            " CREATE TABLE " + TABLE_NAME_STUDENT +
                    "(" + COLUMNAS_STUDENT[0] + " INTEGER PRIMARY KEY," //id
                    + COLUMNAS_STUDENT[1] + " TEXT,"            //nombre
                    + COLUMNAS_STUDENT[2] + " TEXT,"            //apellidos
                    + COLUMNAS_STUDENT[3] + " TEXT,"            //email
                    + COLUMNAS_STUDENT[4] + " BLOB);";          //foto
    private static final String CREATE_TABLE_GRADES =
            "CREATE TABLE " + TABLE_NAME_GRADES +
                    " (" + COLUMNAS_GRADES[0] + " INTEGER PRIMARY KEY,"  //id_estudiante
                    + COLUMNAS_GRADES[1] + " INT,"              //id_assignatura
                    + COLUMNAS_GRADES[2] + " REAL,"             //primeraevaluacion    //REAL en SQLite para trabajar los decimales
                    + COLUMNAS_GRADES[3] + " REAL,"            //segundaevaluacion
                    + " FOREIGN KEY (" + COLUMNAS_GRADES[0] + ") REFERENCES " + TABLE_NAME_STUDENT + "(" + COLUMNAS_STUDENT[0] + "),"
                    + " FOREIGN KEY (" + COLUMNAS_GRADES[1] + ") REFERENCES " + TABLE_NAME_SUBJECT + "(" + COLUMNAS_SUBJECT[0] + "));";
    private static final String CREATE_TABLE_STUDENT_SUBJECT =
            "CREATE TABLE " + TABLE_NAME_STUDENT_SUBJECT +
                    " (" + COLUMNAS_STUDENT_SUBJECT[0] + " INT,"     //id_alumno
                    + COLUMNAS_STUDENT_SUBJECT[1] + " INT,"     //id_asig
                    + " FOREIGN KEY (" + COLUMNAS_STUDENT_SUBJECT[0] + ") REFERENCES " + TABLE_NAME_STUDENT + "(" + COLUMNAS_STUDENT[0] + "),"
                    + " FOREIGN KEY (" + COLUMNAS_STUDENT_SUBJECT[1] + ") REFERENCES " + TABLE_NAME_SUBJECT + "(" + COLUMNAS_SUBJECT[0] + "));";
    private static final String CREATE_TABLE_ATTENDENCE =
            "CREATE TABLE " + TABLE_NAME_ATTENDENCE +
                    " (" + COLUMNAS_ATTENDENCE[0] + " INTEGER PRIMARY KEY,"  //id
                    + COLUMNAS_ATTENDENCE[1] + " TEXT,"             //id_estudiante
                    + COLUMNAS_ATTENDENCE[2] + " INT,"              //id_assignatura
                    + COLUMNAS_ATTENDENCE[3] + " TEXT,"             //fecha
                    + " FOREIGN KEY (" + COLUMNAS_ATTENDENCE[1] + ") REFERENCES " + TABLE_NAME_STUDENT + "(" + COLUMNAS_STUDENT[0] + "),"
                    + " FOREIGN KEY (" + COLUMNAS_ATTENDENCE[2] + ") REFERENCES " + TABLE_NAME_SUBJECT + "(" + COLUMNAS_SUBJECT[0] + "));";

    // El contexto de la aplicación
    private static Context context;
    // La instancia de la base de datos que nos
// proporcionará el Helper (ya sea abriendo una base de
// datos ya existente, creándola si no existe, o actualizándola
// en el caso de algún cambio de versión)
    private SQLiteDatabase db;

    //instance that all the other clases will be working with
    private static SQLite_Helper instance = null;
    // Este atributo se utilizará durante la inserción
    private SQLiteStatement insertStatement, updateStatement;

    public static SQLite_Helper getInstance(Context ct) {
        //this is called the singleton pattern, you know you are not going to use more then one instance of the same class
        if (instance == null) {
            instance = new SQLite_Helper(ct);
        } else {
            context = ct;
        }
        return instance;
    }

    /**
     * @param context <p>Obtenemos un puntero a una base de datos sobre la que poder
     *                escribir mediante la clase MiOpenHelper, que es una clase
     *                privada definida dentro de DataHelper</p>
     *                <p> Obtenemos un puntero a una base de datos sobre la que poder
     *                escribir mediante la clase MiOpenHelper, que es una clase
     *                privada definida dentro de DataHelper</p>
     *                <p>La inserción se realizará mediante lo que se conoce mediante
     *                una sentencia SQL compilada. Asociamos al objeto insertStatement
     *                el código SQL definido en la constante INSERT. Obsérvese que
     *                este código SQL se trata de una sentencia SQL genérica,     parametrizada
     *                mediante el símbolo ?</p>
     */
    private SQLite_Helper(Context context) {
        this.context = context;
// Obtenemos un puntero a una base de datos sobre la que poder
// escribir mediante la clase MiOpenHelper, que es una clase
// privada definida dentro de DataHelper
        MiOpenHelper openHelper = new MiOpenHelper(this.context);
        this.db = openHelper.getWritableDatabase();
    }

    /**
     * El metodo para insertar usuarios, se usara en la "Activity" donde un usuario se
     * crea una cuenta.
     *
     * @param usuario
     * @param contrasena
     * @param nombre
     * @param apellidos
     * @param email
     * @return
     */
    public long insertUser(String usuario, String contrasena, String nombre, String apellidos, String email) {
// Damos valor a los dos elementos genéricos (indicados por el símbolo ?) de la sentencia de inserción compilada mediante bind
        this.insertStatement = this.db.compileStatement(INSERT_USER);
        this.insertStatement.bindString(1, usuario);
        this.insertStatement.bindString(2, contrasena);
        this.insertStatement.bindString(3, nombre);
        this.insertStatement.bindString(4, apellidos);
        this.insertStatement.bindString(5, email);
        // Y llevamos a cabo la inserción
        return this.insertStatement.executeInsert();
    }

    /**
     * <p>El metodo para insertar asignatruras.</p>
     *
     * @param assignatura
     * @return
     */
    public long insertSubject(String assignatura) {
        this.insertStatement = this.db.compileStatement(INSERT_SUBJECT);

        this.insertStatement.bindString(1, assignatura);
        return this.insertStatement.executeInsert();
    }

    /**
     * <p>El metodo para insertar asignatruras.</p>
     *
     * @param nombre
     * @param apellidos
     * @param email
     * @param foto
     * @return
     */
    public long insertStudent(String nombre, String apellidos, String email, byte[] foto) {
        this.insertStatement = this.db.compileStatement(INSERT_STUDENT);

        this.insertStatement.bindString(1, nombre);
        this.insertStatement.bindString(2, apellidos);
        this.insertStatement.bindString(3, email);
        this.insertStatement.bindBlob(4, foto);//something wrong here


        return this.insertStatement.executeInsert();
    }

    /**
     * <p>El metodo para insertar notas.</p>
     *
     * @param idAsig
     * @param pe
     * @param se
     * @return
     */
    public long insertGrades(int idAsig, double pe, double se) {
        this.insertStatement = this.db.compileStatement(INSERT_GRADES);

        this.insertStatement.bindLong(1, idAsig);
        this.insertStatement.bindDouble(2, pe);
        this.insertStatement.bindDouble(3, se);

        return this.insertStatement.executeInsert();
    }

    /**
     * <p>El metodo para insertar alumnos por asignatura.</p>
     *
     * @param idAlum
     * @param idAsig
     * @return
     */
    public long insertStuSub(int idAlum, int idAsig) {
        this.insertStatement = this.db.compileStatement(INSERT_STUDENT_SUBJECT);
        this.insertStatement.bindLong(0, idAlum);
        this.insertStatement.bindLong(1, idAsig);

        return this.insertStatement.executeInsert();
    }

    /**
     * <p>El metodo para insertar faltas.</p>
     *
     * @param idStud
     * @param idAsig
     * @param date
     * @return
     */
    public long insertAttendence(String idStud, int idAsig, String date) {
        this.insertStatement = this.db.compileStatement(INSERT_ATTENDENCE);

        this.insertStatement.bindString(1, idStud);
        this.insertStatement.bindLong(2, idAsig);
        this.insertStatement.bindString(3, date);

        return this.insertStatement.executeInsert();
    }


    /**
     * <p>El metodo para cojer la contraseña del base de datos</p>
     *
     * @param usuario
     * @return
     * @throws UserNotFoundException
     */
    public String getUserPassword(String usuario) throws UserNotFoundException {
        String usario[] = {usuario};
        Cursor cursor = db.query(
                TABLE_NAME_USER,
                COLUMNAS_USER,
                COLUMNAS_USER[0] + "=?",
                usario,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            return cursor.getString(1);
        }
        throw new UserNotFoundException();
    }

    /**
     * <p>El metodo para comprobar si en usuario exsiste.</p>
     *
     * @param usuario
     * @return
     */
    public boolean userExiste(String usuario) {
        String usuarios[] = {usuario};
        Cursor cursor = db.query(
                TABLE_NAME_USER,
                COLUMNAS_USER,
                COLUMNAS_USER[0] + "=?",
                usuarios,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>El metodo para llamar al usuario.</p>
     *
     * @param usuario
     * @return
     * @throws UserNotFoundException
     */
    public String SELECT_USER(String usuario) throws UserNotFoundException {
        String usario[] = {usuario};
        Cursor cursor = db.query(
                TABLE_NAME_USER,
                COLUMNAS_USER,
                COLUMNAS_USER[0] + "=?",
                usario,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            return cursor.getString(2);
        }
        throw new UserNotFoundException();
    }

    /**
     * <p>El metodo para llamar a la asignatura.</p>
     *
     * @param asignatura
     * @return
     * @throws UserNotFoundException
     */
    public String SELECT_ASIGNATIRA(String asignatura) throws UserNotFoundException {
        String asigna[] = {asignatura};
        Cursor cursor = db.query(
                TABLE_NAME_SUBJECT,
                COLUMNAS_USER,
                COLUMNAS_USER[0] + "=?",
                asigna,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            return cursor.getString(2);
        }
        throw new UserNotFoundException();
    }
// TODO: 6/8/2019 makes app fail/stop, won't save onto list you created 
    /**
     * <p>El metodo para agregar asignatuiras a alumnos en la lista.</p>
     *
     * @param listData
     * @return
     * @throws Exception
     */
    public List<Integer> getAsignatura(String[] listData) throws Exception {
        String subject[] = listData;
        List<String> list = new ArrayList<String>();
        Cursor cursor = db.query(
                TABLE_NAME_SUBJECT,     //from
                COLUMNAS_SUBJECT,       //select
                COLUMNAS_SUBJECT[1] + " in (?,?)", //where
                subject,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
                //todo
                //return cursor.getColumnName();
                list.add(cursor.getString(0));
                list.add(cursor.getString(1));
                //list.add(cursor.getInt(1).toString())
                //list.setId(Integer.parseInt(cursor.getString(0)));
                //list.setValue(cursor.getString(1));
                //subject.add(lang);
            } while (cursor.moveToNext());

        }
        throw new Exception();
    }


    /**
     * <p>El metodo para selecionar todos los usuarios.</p>
     * <p>La siguiente instrucción almacena en un cursor todos los valores de las columnas indicadas en COLUMNAS de la tabla TABLE_NAME</p>
     * <p>El scursor es un iterador que nos permite ir recorriendo los resultados devueltos secuencialmente</p>
     *
     * @return
     */
    public List<String> selectAllUsers() {
        List<String> list = new ArrayList<String>();

        Cursor cursor = db.query(
                TABLE_NAME_USER,
                COLUMNAS_USER,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            do {
// Añadimos a la lista que devolveremos como salida
// del método el nombre de la ciudad en la posición            actual

                list.add(cursor.getString(0));
                list.add(cursor.getString(1));

// El método moveToNext devolverá false en el caso de que se
// haya llegado al final
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    /**
     * <p>El metodo para selecionar un estudiante.</p>
     *
     * @param id
     * @return
     */
    public StudentItem selectStudent(int id) {
        //we pass the id that we want to find into "args"
        String[] args = new String[]{Integer.toString(id)};
        Cursor cursor = db.query(
                TABLE_NAME_STUDENT,
                COLUMNAS_STUDENT,
                COLUMNAS_STUDENT[0] + "=?",
                args,
                null,
                null,
                null);

        StudentItem si = null;
        //if there exsists a result, we get into the if statment
        if (cursor.moveToFirst()) {
            si = new StudentItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return si;
    }


    /**
     * <p>El metodo para selecionar todos los estudiantes.</p>
     *
     * @return
     */
    public List<StudentItem> selectAllStudents() {
        List<StudentItem> list = new ArrayList<StudentItem>();
// La siguiente instrucción almacena en un cursor todos los valores
// de las columnas indicadas en COLUMNAS de la tabla TABLE_NAME
        Cursor cursor = db.query(
                TABLE_NAME_STUDENT,
                COLUMNAS_STUDENT,
                null,
                null,
                null,
                null,
                null);
// El scursor es un iterador que nos permite ir recorriendo
// los resultados devueltos secuencialmente
        if (cursor.moveToFirst()) {
            do {
// Añadimos a la lista que devolveremos como salida
// del método el nombre de la ciudad en la posición            actual
                StudentItem si = new StudentItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getBlob(4));
                list.add(si);

                // El método moveToNext devolverá false en el caso de que se haya llegado al final
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    /**
     * <p>El metodo para selecionar todas las asignaturas.</p>
     *
     * @return
     */
    public List<String> selectAllSubjects() {
        List<String> list = new ArrayList<String>();
// La siguiente instrucción almacena en un cursor todos los valores
// de las columnas indicadas en COLUMNAS de la tabla TABLE_NAME
        Cursor cursor = db.query(
                TABLE_NAME_SUBJECT,
                COLUMNAS_SUBJECT,
                null,
                null,
                null,
                null,
                null);
// El scursor es un iterador que nos permite ir recorriendo
// los resultados devueltos secuencialmente
        if (cursor.moveToFirst()) {
            do {
// Añadimos a la lista que devolveremos como salida
// del método el nombre de la ciudad en la posición            actual

                list.add(cursor.getString(1));

                // El método moveToNext devolverá false en el caso de que se
// haya llegado al final
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

/******* method for updating data   *******/

    /**
     * <p>El metodo para borrar usuarios</p>
     *
     * @param usuario
     * @param contrasena
     * @param nombre
     * @param apellidos
     * @param email
     * @return
     */

    public long updateUser(String usuario, String contrasena, String nombre, String apellidos, String email) {
        this.updateStatement = this.db.compileStatement(UPDATE_USER);
        this.updateStatement.bindString(1, usuario);
        this.updateStatement.bindString(2, contrasena);
        this.updateStatement.bindString(3, nombre);
        this.updateStatement.bindString(4, apellidos);
        this.updateStatement.bindString(5, email);
        this.updateStatement.bindString(6, usuario); //we bind here for the 'WHERE CLAUSE'

        return this.updateStatement.executeUpdateDelete();
    }

    /**
     * <p>El metodo para updating aignaturas.</p>
     *
     * @param id
     * @param assignatura
     * @return
     */
    public long updateSubject(int id, String assignatura) {
        this.updateStatement = this.db.compileStatement(UPDATE_SUBJECT);
        this.updateStatement.bindLong(2, id);
        this.updateStatement.bindString(1, assignatura);

        return this.updateStatement.executeUpdateDelete();
    }

    /**
     * <p>El methodo para updating estudiantes </p>
     *
     * @param id
     * @param nombre
     * @param apellidos
     * @param email
     * @param foto
     * @return
     */
    public long updateStudent(int id, String nombre, String apellidos, String email, byte[] foto) {
        this.updateStatement = this.db.compileStatement(UPDATE_STUDENT);

        this.updateStatement.bindString(1, nombre);
        this.updateStatement.bindString(2, apellidos);
        this.updateStatement.bindString(3, email);
        this.updateStatement.bindBlob(4, foto);//here this bindBlod, this dude, is doing the hard work for us by doing the convervion.
        this.updateStatement.bindLong(5, id);
        System.out.println(this.updateStatement.toString());
        return this.updateStatement.executeUpdateDelete();
    }

    /**
     * <p>El methodo para updating notas </p>
     *
     * @param idStu
     * @param idAsig
     * @param pe
     * @param se
     * @return
     */
    public long updateGrades(int idStu, int idAsig, double pe, double se) {
        this.updateStatement = this.db.compileStatement(UPDATE_GRADES);
        this.updateStatement.bindLong(3, idStu);
        this.updateStatement.bindLong(4, idAsig);
        this.updateStatement.bindDouble(1, pe);
        this.updateStatement.bindDouble(2, se);

        return this.updateStatement.executeUpdateDelete();
    }

    /**
     * <p>El method para updating Estudiante/Asignatura</p>
     *
     * @param idAlum
     * @param idAsig
     * @return
     */
    public long updateStuSub(int idAlum, int idAsig) {
        this.updateStatement = this.db.compileStatement(UPDATE_STUDENT_SUBJECT);
        this.updateStatement.bindLong(1, idAlum);
        this.updateStatement.bindLong(3, idAlum);
        this.updateStatement.bindLong(2, idAsig);
        this.updateStatement.bindLong(4, idAsig);

        return this.updateStatement.executeUpdateDelete();
    }

    /**
     * <p>El methodo parta updating faltas.</p>
     *
     * @param id
     * @param idStud
     * @param idAsig
     * @param date
     * @return
     */
    public long updateAttendence(int id, int idStud, int idAsig, String date) {
        this.updateStatement = this.db.compileStatement(UPDATE_ATTENDENCE);
        this.updateStatement.bindLong(1, idStud);
        this.updateStatement.bindLong(2, idAsig);
        this.updateStatement.bindString(3, date);
        this.updateStatement.bindLong(4, id);

        return this.updateStatement.executeUpdateDelete();
    }

/****** method for deleting data NOT FINISHED NOR IMPLEMENTED  ******/
// TODO: 6/8/2019

    /**
     * <p>El methodo para borrar usuario.</p>
     *
     * @param idUsuario
     * @return
     */
    public long deleteUser(String idUsuario) {
        this.updateStatement = this.db.compileStatement(DELETE_USER);
        this.updateStatement.bindString(1, idUsuario);
        return this.updateStatement.executeUpdateDelete();
    }

    /**
     * <p>El metodo para borrar asignatura.</p>
     *
     * @param idAsigna
     * @return
     */
    public long deleteSubject(int idAsigna) {
        this.updateStatement = this.db.compileStatement(DELETE_SUBJECT);
        this.updateStatement.bindLong(1, idAsigna);
        return 0;
    }

    public long deleteStudent(int idStudent) {
        this.updateStatement = this.db.compileStatement(DELETE_STUDENT);
        this.updateStatement.bindLong(1, idStudent);
        return this.updateStatement.executeUpdateDelete();
    }

    public long deleteGrades(int idStu, int idAsigna) {
        this.updateStatement = this.db.compileStatement(DELETE_GRADES);
        this.updateStatement.bindLong(1, idStu);
        this.updateStatement.bindLong(2, idAsigna);
        return this.updateStatement.executeUpdateDelete();
    }

    public long deleteStuSub(int id_alumno, int id_asig) {
        this.updateStatement = this.db.compileStatement(DELETE_STUDENT_SUBJECT);
        this.updateStatement.bindLong(1, id_alumno);
        this.updateStatement.bindLong(2, id_asig);
        return this.updateStatement.executeUpdateDelete();
    }

    public long deleteAttendence(int idAttend) {
        this.updateStatement = this.db.compileStatement(DELETE_ATTENDENCE);
        this.updateStatement.bindLong(1, idAttend);
        return this.updateStatement.executeUpdateDelete();
    }
// TODO: 5/24/2019  
    /*****  method for deleting all *****/
    public long deleteAll() {

// En este caso hacemos uso de un método de la instancia de la base de datos para realizar el borrado. Existen también métodos para hacer queries y otras operaciones con la base de datos
        long rowsDeleted = 0;
        rowsDeleted += db.delete(TABLE_NAME_USER, null, null);
        rowsDeleted += db.delete(TABLE_NAME_SUBJECT, null, null);
        rowsDeleted += db.delete(TABLE_NAME_STUDENT, null, null);
        rowsDeleted += db.delete(TABLE_NAME_GRADES, null, null);
        rowsDeleted += db.delete(TABLE_NAME_STUDENT_SUBJECT, null, null);
        rowsDeleted += db.delete(TABLE_NAME_ATTENDENCE, null, null);

        return rowsDeleted;
    }

    public int selectLastInsertedStudentID() {
        //we need to get the ID with the highest value
        String[] topID = new String[]{"MAX(id)"};


        Cursor cursor = db.query(TABLE_NAME_STUDENT, topID, null, null, null, null, null);

        int id = -1;
        //if there exsists a result, we get into the if statment
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return id;
    }

    /**
     * <p>Esta clase privada del DataHelper se encarga de proporcionar una instancia de base de datos a DataHelper sobre la que poder trabajar.</p>
     */
    private static class MiOpenHelper extends SQLiteOpenHelper {
        MiOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * <p>Este método se utilizará en el caso en el que la base de datos no existiera.</p>
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_STUDENT);
            db.execSQL(CREATE_TABLE_SUBJECT);
            db.execSQL(CREATE_TABLE_STUDENT_SUBJECT);
            db.execSQL(CREATE_TABLE_ATTENDENCE);
            db.execSQL(CREATE_TABLE_GRADES);
        }


        @Override


// TODO: 5/24/2019
        /**
         *
         * <p>Este método se ejecutará en el caso en el que se cambie el valor de la constante DATABASE_VERSION.
         * En este caso se borra la base de datos anterior antes de crear una nueva, pero lo ideal sería transferir
         * los datos desde la versión anterior a la nueva</p>
         * @param db
         * @param oldVersion
         * @param newVersion
         *
         */
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int
                newVersion) {
            Log.w("SQL", "onUpgrade: eliminando tabla si existe y creándola de nuevo");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SUBJECT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GRADES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT_SUBJECT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ATTENDENCE);
            onCreate(db);
        }
    }
}
