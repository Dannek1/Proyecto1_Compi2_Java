/* Analizador_XML.java */
/* Generated By:JJTree&JavaCC: Do not edit this line. Analizador_XML.java */
package Analizador_XML;


import java.io.*;


public class Analizador_XML/*@bgen(jjtree)*/implements Analizador_XMLTreeConstants, Analizador_XMLConstants {/*@bgen(jjtree)*/
  protected JJTAnalizador_XMLState jjtree = new JJTAnalizador_XMLState();

  final public SimpleNode Programa() throws ParseException {/*@bgen(jjtree) PROGRAMA */
  SimpleNode jjtn000 = new SimpleNode(JJTPROGRAMA);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(Propiedasdes);
      inicio();
      jj_consume_token(0);
jjtree.closeNodeScope(jjtn000, true);
                                     jjtc000 = false;
{if ("" != null) return jjtn000;}
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
    throw new Error("Missing return statement in function");
  }

  final public void inicio() throws ParseException {/*@bgen(jjtree) inicio */
  SimpleNode jjtn000 = new SimpleNode(JJTINICIO);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MAESTRO:{
        jj_consume_token(MAESTRO);
        maestro();
        jj_consume_token(C_MAESTRO);
        break;
        }
      case BASE:{
        jj_consume_token(BASE);
        base();
        jj_consume_token(C_BASE);
        break;
        }
      case TABLA:{
        jj_consume_token(TABLA);
        tabla();
        jj_consume_token(C_TABLA);
        break;
        }
      case METODO:{
        jj_consume_token(METODO);
        label_1:
        while (true) {
          metodos();
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case PROC:{
            ;
            break;
            }
          default:
            jj_la1[0] = jj_gen;
            break label_1;
          }
        }
        jj_consume_token(C_METODO);
        break;
        }
      case OBJETO:{
        jj_consume_token(OBJETO);
        label_2:
        while (true) {
          objetos();
          switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
          case OBJ:{
            ;
            break;
            }
          default:
            jj_la1[1] = jj_gen;
            break label_2;
          }
        }
        jj_consume_token(C_OBJETO);
        break;
        }
      case V_MAESTRO:{
        jj_consume_token(V_MAESTRO);
        break;
        }
      case V_BASE:{
        jj_consume_token(V_BASE);
        break;
        }
      case V_TABLA:{
        jj_consume_token(V_TABLA);
        break;
        }
      case V_METODO:{
        jj_consume_token(V_METODO);
        break;
        }
      case V_OBJETO:{
        jj_consume_token(V_OBJETO);
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void maestro() throws ParseException {/*@bgen(jjtree) maestro */
  SimpleNode jjtn000 = new SimpleNode(JJTMAESTRO);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      label_3:
      while (true) {
        jj_consume_token(DB);
        db();
        jj_consume_token(C_DB);
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case DB:{
          ;
          break;
          }
        default:
          jj_la1[3] = jj_gen;
          break label_3;
        }
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void db() throws ParseException {/*@bgen(jjtree) db */
  SimpleNode jjtn000 = new SimpleNode(JJTDB);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      nombre();
      ruta();
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void base() throws ParseException {/*@bgen(jjtree) base */
  SimpleNode jjtn000 = new SimpleNode(JJTBASE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      procB();
      obB();
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case TABLA:{
          ;
          break;
          }
        default:
          jj_la1[4] = jj_gen;
          break label_4;
        }
        tabB();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void tabB() throws ParseException {/*@bgen(jjtree) tabB */
  SimpleNode jjtn000 = new SimpleNode(JJTTABB);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(TABLA);
      nombre();
      ruta();
      jj_consume_token(ROWS);
      label_5:
      while (true) {
        rows();
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case INICIO:{
          ;
          break;
          }
        default:
          jj_la1[5] = jj_gen;
          break label_5;
        }
      }
      jj_consume_token(C_ROWS);
      jj_consume_token(C_TABLA);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void procB() throws ParseException {/*@bgen(jjtree) procB */
  SimpleNode jjtn000 = new SimpleNode(JJTPROCB);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(PROCEDURE);
      ruta();
      jj_consume_token(C_PROCEDURE);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void obB() throws ParseException {/*@bgen(jjtree) obB */
  SimpleNode jjtn000 = new SimpleNode(JJTOBB);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(OBJECT);
      ruta();
      jj_consume_token(C_OBJECT);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void rows() throws ParseException {/*@bgen(jjtree) rows */
  SimpleNode jjtn000 = new SimpleNode(JJTROWS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(INICIO);
      tipo_dato();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case R_ATRIBUTOS:{
        atributos();
        break;
        }
      default:
        jj_la1[6] = jj_gen;
        ;
      }
      jj_consume_token(CIERRE);
      r_atributo();
      jj_consume_token(FIN);
      tipo_dato();
      jj_consume_token(CIERRE);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void metodos() throws ParseException {/*@bgen(jjtree) metodos */
  SimpleNode jjtn000 = new SimpleNode(JJTMETODOS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(PROC);
      nombre();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case PARAMS:{
        params();
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        ;
      }
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case TIPO_RETURN:{
        tipoR();
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        ;
      }
      intrucciones();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case RETURN:{
        retorno();
        break;
        }
      default:
        jj_la1[9] = jj_gen;
        ;
      }
      jj_consume_token(C_PROC);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void tipoR() throws ParseException {/*@bgen(jjtree) tipoR */
  SimpleNode jjtn000 = new SimpleNode(JJTTIPOR);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(TIPO_RETURN);
      tipo_dato();
      jj_consume_token(C_TIPO_RETURN);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void params() throws ParseException {/*@bgen(jjtree) params */
  SimpleNode jjtn000 = new SimpleNode(JJTPARAMS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(PARAMS);
      label_6:
      while (true) {
        rows();
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case INICIO:{
          ;
          break;
          }
        default:
          jj_la1[10] = jj_gen;
          break label_6;
        }
      }
      jj_consume_token(C_PARAMS);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void intrucciones() throws ParseException {/*@bgen(jjtree) intrucciones */
  SimpleNode jjtn000 = new SimpleNode(JJTINTRUCCIONES);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(SRC);
      label_7:
      while (true) {
        r_atributo();
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case Atributo:{
          ;
          break;
          }
        default:
          jj_la1[11] = jj_gen;
          break label_7;
        }
      }
      jj_consume_token(C_SRC);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void retorno() throws ParseException {/*@bgen(jjtree) retorno */
  SimpleNode jjtn000 = new SimpleNode(JJTRETORNO);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(RETURN);
      r_atributo();
      jj_consume_token(C_RETURN);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void objetos() throws ParseException {/*@bgen(jjtree) objetos */
  SimpleNode jjtn000 = new SimpleNode(JJTOBJETOS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(OBJ);
      nombre();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ATTR:{
        attribs();
        break;
        }
      default:
        jj_la1[12] = jj_gen;
        ;
      }
      jj_consume_token(C_OBJ);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void attribs() throws ParseException {/*@bgen(jjtree) attribs */
  SimpleNode jjtn000 = new SimpleNode(JJTATTRIBS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(ATTR);
      rows();
      jj_consume_token(C_ATTR);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void tipo_dato() throws ParseException {/*@bgen(jjtree) TIPO_DATO */
        SimpleNode jjtn000 = new SimpleNode(JJTTIPO_DATO);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case R_TEXT:{
        t = jj_consume_token(R_TEXT);
jjtree.closeNodeScope(jjtn000, true);
                     jjtc000 = false;
jjtn000.setName(t.image);
        break;
        }
      case R_INTEGER:{
        t = jj_consume_token(R_INTEGER);
jjtree.closeNodeScope(jjtn000, true);
                        jjtc000 = false;
jjtn000.setName(t.image);
        break;
        }
      case R_DOUBLE:{
        t = jj_consume_token(R_DOUBLE);
jjtree.closeNodeScope(jjtn000, true);
                       jjtc000 = false;
jjtn000.setName(t.image);
        break;
        }
      case R_BOOL:{
        t = jj_consume_token(R_BOOL);
jjtree.closeNodeScope(jjtn000, true);
                     jjtc000 = false;
jjtn000.setName(t.image);
        break;
        }
      case R_DATE:{
        t = jj_consume_token(R_DATE);
jjtree.closeNodeScope(jjtn000, true);
                     jjtc000 = false;
jjtn000.setName(t.image);
        break;
        }
      case R_DATETIME:{
        t = jj_consume_token(R_DATETIME);
jjtree.closeNodeScope(jjtn000, true);
                         jjtc000 = false;
jjtn000.setName(t.image);
        break;
        }
      default:
        jj_la1[13] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
if (jjtc000) {
             jjtree.closeNodeScope(jjtn000, true);
           }
    }
  }

  final public void atributos() throws ParseException {/*@bgen(jjtree) atributos */
  SimpleNode jjtn000 = new SimpleNode(JJTATRIBUTOS);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(R_ATRIBUTOS);
      jj_consume_token(IGUAL);
      r_atributo();
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void nombre() throws ParseException {/*@bgen(jjtree) nombre */
  SimpleNode jjtn000 = new SimpleNode(JJTNOMBRE);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(NOMBRE);
      r_atributo();
      jj_consume_token(C_NOMBRE);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void ruta() throws ParseException {/*@bgen(jjtree) ruta */
  SimpleNode jjtn000 = new SimpleNode(JJTRUTA);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(PATH);
      r_atributo();
      jj_consume_token(C_PATH);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void tabla() throws ParseException {/*@bgen(jjtree) tabla */
  SimpleNode jjtn000 = new SimpleNode(JJTTABLA);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(ROW);
      label_8:
      while (true) {
        row();
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case NOMBRE:
        case INICIO:{
          ;
          break;
          }
        default:
          jj_la1[14] = jj_gen;
          break label_8;
        }
      }
      jj_consume_token(C_ROW);
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void row() throws ParseException {/*@bgen(jjtree) row */
  SimpleNode jjtn000 = new SimpleNode(JJTROW);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INICIO:{
        jj_consume_token(INICIO);
        jj_consume_token(ID);
        jj_consume_token(CIERRE);
        r_atributo();
        jj_consume_token(FIN);
        jj_consume_token(ID);
        jj_consume_token(CIERRE);
        break;
        }
      case NOMBRE:{
        jj_consume_token(NOMBRE);
        r_atributo();
        jj_consume_token(C_NOMBRE);
        break;
        }
      default:
        jj_la1[15] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
if (jjtc000) {
            jjtree.clearNodeScope(jjtn000);
            jjtc000 = false;
          } else {
            jjtree.popNode();
          }
          if (jjte000 instanceof RuntimeException) {
            {if (true) throw (RuntimeException)jjte000;}
          }
          if (jjte000 instanceof ParseException) {
            {if (true) throw (ParseException)jjte000;}
          }
          {if (true) throw (Error)jjte000;}
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  final public void r_atributo() throws ParseException {/*@bgen(jjtree) ATRIBUTO */
        SimpleNode jjtn000 = new SimpleNode(JJTATRIBUTO);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(Atributo);
jjtree.closeNodeScope(jjtn000, true);
                      jjtc000 = false;
jjtn000.setName(t.image);
    } finally {
if (jjtc000) {
            jjtree.closeNodeScope(jjtn000, true);
          }
    }
  }

  /** Generated Token Manager. */
  public Analizador_XMLTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[16];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1000000,0x4000000,0x2da14a,0x10,0x2000,0x0,0x0,0x40000000,0x0,0x0,0x0,0x0,0x10000000,0x0,0x400000,0x400000,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x0,0x0,0x100000,0x40000,0x0,0x1,0x4,0x100000,0x800000,0x0,0x3f000,0x100000,0x100000,};
   }

  /** Constructor with InputStream. */
  public Analizador_XML(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Analizador_XML(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new Analizador_XMLTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Analizador_XML(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new Analizador_XMLTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Analizador_XML(Analizador_XMLTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(Analizador_XMLTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 16; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[64];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 16; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 64; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
