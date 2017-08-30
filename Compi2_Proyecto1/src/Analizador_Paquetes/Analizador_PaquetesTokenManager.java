/* Analizador_PaquetesTokenManager.java */
/* Generated By:JJTree&JavaCC: Do not edit this line. Analizador_PaquetesTokenManager.java */
package Analizador_Paquetes;
import java.io.*;

/** Token Manager. */
@SuppressWarnings("unused")public class Analizador_PaquetesTokenManager implements Analizador_PaquetesConstants {

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 13:
         jjmatchedKind = 19;
         return jjMoveStringLiteralDfa1_0(0x40000L);
      case 34:
         jjmatchedKind = 13;
         return jjMoveStringLiteralDfa1_0(0x7f8L);
      case 44:
         return jjStopAtPos(0, 12);
      case 58:
         return jjStopAtPos(0, 11);
      case 91:
         return jjStopAtPos(0, 1);
      case 93:
         return jjStopAtPos(0, 2);
      default :
         return jjMoveNfa_0(1, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 10:
         if ((active0 & 0x40000L) != 0L)
            return jjStopAtPos(1, 18);
         break;
      case 67:
      case 99:
         return jjMoveStringLiteralDfa2_0(active0, 0x20L);
      case 70:
      case 102:
         return jjMoveStringLiteralDfa2_0(active0, 0x400L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x80L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x10L);
      case 80:
      case 112:
         return jjMoveStringLiteralDfa2_0(active0, 0x40L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x200L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa2_0(active0, 0x100L);
      case 86:
      case 118:
         return jjMoveStringLiteralDfa2_0(active0, 0x8L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 65:
      case 97:
         return jjMoveStringLiteralDfa3_0(active0, 0x48L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x200L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x400L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x80L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x30L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x100L);
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 71:
      case 103:
         return jjMoveStringLiteralDfa4_0(active0, 0x10L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x8L);
      case 77:
      case 109:
         return jjMoveStringLiteralDfa4_0(active0, 0x20L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa4_0(active0, 0x400L);
      case 80:
      case 112:
         return jjMoveStringLiteralDfa4_0(active0, 0x200L);
      case 81:
      case 113:
         return jjMoveStringLiteralDfa4_0(active0, 0x140L);
      case 83:
      case 115:
         return jjMoveStringLiteralDfa4_0(active0, 0x80L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 34:
         if ((active0 & 0x400L) != 0L)
            return jjStopAtPos(4, 10);
         break;
      case 65:
      case 97:
         return jjMoveStringLiteralDfa5_0(active0, 0x20L);
      case 73:
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x18L);
      case 76:
      case 108:
         return jjMoveStringLiteralDfa5_0(active0, 0x100L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0x200L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa5_0(active0, 0x80L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa5_0(active0, 0x40L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 34:
         if ((active0 & 0x100L) != 0L)
            return jjStopAtPos(5, 8);
         break;
      case 68:
      case 100:
         return jjMoveStringLiteralDfa6_0(active0, 0x8L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa6_0(active0, 0x40L);
      case 78:
      case 110:
         return jjMoveStringLiteralDfa6_0(active0, 0x30L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa6_0(active0, 0x280L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 34:
         if ((active0 & 0x10L) != 0L)
            return jjStopAtPos(6, 4);
         break;
      case 65:
      case 97:
         return jjMoveStringLiteralDfa7_0(active0, 0x8L);
      case 68:
      case 100:
         return jjMoveStringLiteralDfa7_0(active0, 0x20L);
      case 84:
      case 116:
         return jjMoveStringLiteralDfa7_0(active0, 0x240L);
      case 85:
      case 117:
         return jjMoveStringLiteralDfa7_0(active0, 0x80L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
private int jjMoveStringLiteralDfa7_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 67:
      case 99:
         return jjMoveStringLiteralDfa8_0(active0, 0x80L);
      case 69:
      case 101:
         return jjMoveStringLiteralDfa8_0(active0, 0x240L);
      case 79:
      case 111:
         return jjMoveStringLiteralDfa8_0(active0, 0x20L);
      case 82:
      case 114:
         return jjMoveStringLiteralDfa8_0(active0, 0x8L);
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
private int jjMoveStringLiteralDfa8_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(6, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(7, active0);
      return 8;
   }
   switch(curChar)
   {
      case 34:
         if ((active0 & 0x8L) != 0L)
            return jjStopAtPos(8, 3);
         else if ((active0 & 0x20L) != 0L)
            return jjStopAtPos(8, 5);
         else if ((active0 & 0x40L) != 0L)
            return jjStopAtPos(8, 6);
         else if ((active0 & 0x200L) != 0L)
            return jjStopAtPos(8, 9);
         break;
      case 67:
      case 99:
         return jjMoveStringLiteralDfa9_0(active0, 0x80L);
      default :
         break;
   }
   return jjStartNfa_0(7, active0);
}
private int jjMoveStringLiteralDfa9_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(7, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(8, active0);
      return 9;
   }
   switch(curChar)
   {
      case 73:
      case 105:
         return jjMoveStringLiteralDfa10_0(active0, 0x80L);
      default :
         break;
   }
   return jjStartNfa_0(8, active0);
}
private int jjMoveStringLiteralDfa10_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(8, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(9, active0);
      return 10;
   }
   switch(curChar)
   {
      case 79:
      case 111:
         return jjMoveStringLiteralDfa11_0(active0, 0x80L);
      default :
         break;
   }
   return jjStartNfa_0(9, active0);
}
private int jjMoveStringLiteralDfa11_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(9, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(10, active0);
      return 11;
   }
   switch(curChar)
   {
      case 78:
      case 110:
         return jjMoveStringLiteralDfa12_0(active0, 0x80L);
      default :
         break;
   }
   return jjStartNfa_0(10, active0);
}
private int jjMoveStringLiteralDfa12_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(10, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(11, active0);
      return 12;
   }
   switch(curChar)
   {
      case 34:
         if ((active0 & 0x80L) != 0L)
            return jjStopAtPos(12, 7);
         break;
      default :
         break;
   }
   return jjStartNfa_0(11, active0);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 4;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
               case 0:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 14)
                     kind = 14;
                  { jjCheckNAdd(0); }
                  break;
               case 2:
                  { jjAddStates(0, 1); }
                  break;
               case 3:
                  if (curChar == 59 && kind > 15)
                     kind = 15;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((0x7fffffe07ffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(2, 3); }
                  break;
               case 2:
                  { jjCheckNAddTwoStates(2, 3); }
                  break;
               case 3:
                  if (curChar == 125 && kind > 15)
                     kind = 15;
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(0, 1); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 4 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   2, 3, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", "\133", "\135", null, null, null, null, null, null, null, null, "\72", 
"\54", "\42", null, null, null, null, null, null, null, null, };
protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100001600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

    /** Constructor. */
    public Analizador_PaquetesTokenManager(SimpleCharStream stream){

      if (SimpleCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

    input_stream = stream;
  }

  /** Constructor. */
  public Analizador_PaquetesTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  public void ReInit(SimpleCharStream stream)
  {
    jjmatchedPos = jjnewStateCnt = 0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 4; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  public void ReInit(SimpleCharStream stream, int lexState)
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public void SwitchTo(int lexState)
  {
    if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0xffffL, 
};
static final long[] jjtoSkip = {
   0x3f0000L, 
};
    protected SimpleCharStream  input_stream;

    private final int[] jjrounds = new int[4];
    private final int[] jjstateSet = new int[2 * 4];

    
    protected char curChar;
}
