// Generated from /Users/liyang/IdeaProjects/sourceCode_utilityDemo_designPattern/spring_source_test/src/main/resources/dsl/SimpleCalculator.g4 by ANTLR 4.13.2
package com.dsl;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpleCalculatorParser}.
 */
public interface SimpleCalculatorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SimpleCalculatorParser#cal}.
	 * @param ctx the parse tree
	 */
	void enterCal(SimpleCalculatorParser.CalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCalculatorParser#cal}.
	 * @param ctx the parse tree
	 */
	void exitCal(SimpleCalculatorParser.CalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code add}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAdd(SimpleCalculatorParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by the {@code add}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAdd(SimpleCalculatorParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by the {@code sub}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSub(SimpleCalculatorParser.SubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code sub}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSub(SimpleCalculatorParser.SubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mul}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterMul(SimpleCalculatorParser.MulContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mul}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitMul(SimpleCalculatorParser.MulContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ID}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterID(SimpleCalculatorParser.IDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ID}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitID(SimpleCalculatorParser.IDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code first}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterFirst(SimpleCalculatorParser.FirstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code first}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitFirst(SimpleCalculatorParser.FirstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code int}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterInt(SimpleCalculatorParser.IntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code int}
	 * labeled alternative in {@link SimpleCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitInt(SimpleCalculatorParser.IntContext ctx);
}