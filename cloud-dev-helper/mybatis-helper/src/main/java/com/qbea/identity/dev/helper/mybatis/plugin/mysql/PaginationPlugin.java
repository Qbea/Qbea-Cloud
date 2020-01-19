package com.qbea.identity.dev.helper.mybatis.plugin.mysql;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.List;
import java.util.stream.Collectors;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

public class PaginationPlugin extends PluginAdapter {
	private String importClassName = "java.util.Date";
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
			IntrospectedTable introspectedTable) {
		topLevelClass.addImportedType(new FullyQualifiedJavaType(
				importClassName));
		// 添加大字段查的方法
        addBlobParamForExample(topLevelClass, introspectedTable);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// 添加date的import
		topLevelClass.addImportedType(new FullyQualifiedJavaType(
				importClassName));
		// 添加无blob字段的构造方法
		//addParameterizedConstructor(topLevelClass,introspectedTable);
		return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
	}





	public boolean validate(List<String> warnings) {
		return true;
	}
	public void addParameterizedConstructor(TopLevelClass topLevelClass,IntrospectedTable introspectedTable){
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setConstructor(true);
		method.setName(topLevelClass.getType().getShortName());

		List<IntrospectedColumn> constructorColumns = introspectedTable
				.getNonBLOBColumns();

		for (IntrospectedColumn introspectedColumn : constructorColumns) {
			method.addParameter(new Parameter(introspectedColumn
					.getFullyQualifiedJavaType(), introspectedColumn
					.getJavaProperty()));
		}

		StringBuilder sb = new StringBuilder();
		if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			boolean comma = false;
			sb.append("super("); //$NON-NLS-1$
			for (IntrospectedColumn introspectedColumn : introspectedTable
					.getPrimaryKeyColumns()) {
				if (comma) {
					sb.append(", "); //$NON-NLS-1$
				} else {
					comma = true;
				}
				sb.append(introspectedColumn.getJavaProperty());
			}
			sb.append(");"); //$NON-NLS-1$
			method.addBodyLine(sb.toString());
		}

		List<IntrospectedColumn> introspectedColumns = introspectedTable.getNonBLOBColumns();

		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			sb.setLength(0);
			sb.append("this."); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" = "); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(';');
			method.addBodyLine(sb.toString());
		}

		topLevelClass.addMethod(method);
	}
    public void addBlobParamForExample(TopLevelClass topLevelClass, IntrospectedTable introspectedTable){
        List<InnerClass> innerClasses = topLevelClass.getInnerClasses();
		final InnerClass answer = innerClasses.stream().filter(item -> {
			return item.getType().toString().equals("Criteria");
		}).collect(Collectors.toList()).get(0);
		for (IntrospectedColumn introspectedColumn : introspectedTable
				.getBLOBColumns()) {
			topLevelClass.addImportedType(introspectedColumn
					.getFullyQualifiedJavaType());

			// here we need to add the individual methods for setting the
			// conditions for a field
			answer.addMethod(getSetNullMethod(introspectedColumn));
			answer.addMethod(getSetNotNullMethod(introspectedColumn));
			answer.addMethod(getSetEqualMethod(introspectedColumn));
			answer.addMethod(getSetNotEqualMethod(introspectedColumn));
			answer.addMethod(getSetGreaterThanMethod(introspectedColumn));
			answer.addMethod(getSetGreaterThenOrEqualMethod(introspectedColumn));
			answer.addMethod(getSetLessThanMethod(introspectedColumn));
			answer.addMethod(getSetLessThanOrEqualMethod(introspectedColumn));

			if (introspectedColumn.isJdbcCharacterColumn()) {
				answer.addMethod(getSetLikeMethod(introspectedColumn));
				answer.addMethod(getSetNotLikeMethod(introspectedColumn));
			}

			answer.addMethod(getSetInOrNotInMethod(introspectedColumn, true));
			answer.addMethod(getSetInOrNotInMethod(introspectedColumn, false));
			answer.addMethod(getSetBetweenOrNotBetweenMethod(
					introspectedColumn, true));
			answer.addMethod(getSetBetweenOrNotBetweenMethod(
					introspectedColumn, false));
		}
	}
	private Method getSetNullMethod(IntrospectedColumn introspectedColumn) {
		return getNoValueMethod(introspectedColumn, "IsNull", "is null"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private Method getSetNotNullMethod(IntrospectedColumn introspectedColumn) {
		return getNoValueMethod(introspectedColumn, "IsNotNull", "is not null"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	private Method getSetEqualMethod(IntrospectedColumn introspectedColumn) {
		return getSingleValueMethod(introspectedColumn, "EqualTo", "="); //$NON-NLS-1$ //$NON-NLS-2$
	}
	private Method getSetNotEqualMethod(IntrospectedColumn introspectedColumn) {
		return getSingleValueMethod(introspectedColumn, "NotEqualTo", "<>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private Method getSetGreaterThanMethod(IntrospectedColumn introspectedColumn) {
		return getSingleValueMethod(introspectedColumn, "GreaterThan", ">"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private Method getSetGreaterThenOrEqualMethod(
			IntrospectedColumn introspectedColumn) {
		return getSingleValueMethod(introspectedColumn,
				"GreaterThanOrEqualTo", ">="); //$NON-NLS-1$ //$NON-NLS-2$
	}
	private Method getSetLessThanMethod(IntrospectedColumn introspectedColumn) {
		return getSingleValueMethod(introspectedColumn, "LessThan", "<"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private Method getSetLessThanOrEqualMethod(
			IntrospectedColumn introspectedColumn) {
		return getSingleValueMethod(introspectedColumn,
				"LessThanOrEqualTo", "<="); //$NON-NLS-1$ //$NON-NLS-2$
	}
	private Method getSetLikeMethod(IntrospectedColumn introspectedColumn) {
		return getSingleValueMethod(introspectedColumn, "Like", "like"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	private Method getSetNotLikeMethod(IntrospectedColumn introspectedColumn) {
		return getSingleValueMethod(introspectedColumn, "NotLike", "not like"); //$NON-NLS-1$ //$NON-NLS-2$
	}


	private Method getSetInOrNotInMethod(IntrospectedColumn introspectedColumn,
										 boolean inMethod) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		FullyQualifiedJavaType type = FullyQualifiedJavaType
				.getNewListInstance();
		if (introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
			type.addTypeArgument(introspectedColumn.getFullyQualifiedJavaType()
					.getPrimitiveTypeWrapper());
		} else {
			type
					.addTypeArgument(introspectedColumn
							.getFullyQualifiedJavaType());
		}

		method.addParameter(new Parameter(type, "values")); //$NON-NLS-1$
		StringBuilder sb = new StringBuilder();
		sb.append(introspectedColumn.getJavaProperty());
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		sb.insert(0, "and"); //$NON-NLS-1$
		if (inMethod) {
			sb.append("In"); //$NON-NLS-1$
		} else {
			sb.append("NotIn"); //$NON-NLS-1$
		}
		method.setName(sb.toString());
		method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
		sb.setLength(0);

		if (introspectedColumn.isJDBCDateColumn()) {
			sb.append("addCriterionForJDBCDate(\""); //$NON-NLS-1$
		} else if (introspectedColumn.isJDBCTimeColumn()) {
			sb.append("addCriterionForJDBCTime(\""); //$NON-NLS-1$
		} else if (stringHasValue(introspectedColumn
				.getTypeHandler())) {
			sb.append("add"); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
			sb.append("Criterion(\""); //$NON-NLS-1$
		} else {
			sb.append("addCriterion(\""); //$NON-NLS-1$
		}

		sb.append(MyBatis3FormattingUtilities
				.getAliasedActualColumnName(introspectedColumn));
		if (inMethod) {
			sb.append(" in"); //$NON-NLS-1$
		} else {
			sb.append(" not in"); //$NON-NLS-1$
		}
		sb.append("\", values, \""); //$NON-NLS-1$
		sb.append(introspectedColumn.getJavaProperty());
		sb.append("\");"); //$NON-NLS-1$
		method.addBodyLine(sb.toString());
		method.addBodyLine("return (Criteria) this;"); //$NON-NLS-1$

		return method;
	}
	private Method getSetBetweenOrNotBetweenMethod(
			IntrospectedColumn introspectedColumn, boolean betweenMethod) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		FullyQualifiedJavaType type = introspectedColumn
				.getFullyQualifiedJavaType();

		method.addParameter(new Parameter(type, "value1")); //$NON-NLS-1$
		method.addParameter(new Parameter(type, "value2")); //$NON-NLS-1$
		StringBuilder sb = new StringBuilder();
		sb.append(introspectedColumn.getJavaProperty());
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		sb.insert(0, "and"); //$NON-NLS-1$
		if (betweenMethod) {
			sb.append("Between"); //$NON-NLS-1$
		} else {
			sb.append("NotBetween"); //$NON-NLS-1$
		}
		method.setName(sb.toString());
		method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
		sb.setLength(0);

		if (introspectedColumn.isJDBCDateColumn()) {
			sb.append("addCriterionForJDBCDate(\""); //$NON-NLS-1$
		} else if (introspectedColumn.isJDBCTimeColumn()) {
			sb.append("addCriterionForJDBCTime(\""); //$NON-NLS-1$
		} else if (stringHasValue(introspectedColumn
				.getTypeHandler())) {
			sb.append("add"); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
			sb.append("Criterion(\""); //$NON-NLS-1$
		} else {
			sb.append("addCriterion(\""); //$NON-NLS-1$
		}

		sb.append(MyBatis3FormattingUtilities
				.getAliasedActualColumnName(introspectedColumn));
		if (betweenMethod) {
			sb.append(" between"); //$NON-NLS-1$
		} else {
			sb.append(" not between"); //$NON-NLS-1$
		}
		sb.append("\", "); //$NON-NLS-1$
		sb.append("value1, value2"); //$NON-NLS-1$
		sb.append(", \""); //$NON-NLS-1$
		sb.append(introspectedColumn.getJavaProperty());
		sb.append("\");"); //$NON-NLS-1$
		method.addBodyLine(sb.toString());
		method.addBodyLine("return (Criteria) this;"); //$NON-NLS-1$

		return method;
	}
	private Method getNoValueMethod(IntrospectedColumn introspectedColumn,
									String nameFragment, String operator) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		StringBuilder sb = new StringBuilder();
		sb.append(introspectedColumn.getJavaProperty());
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		sb.insert(0, "and"); //$NON-NLS-1$
		sb.append(nameFragment);
		method.setName(sb.toString());
		method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
		sb.setLength(0);
		sb.append("addCriterion(\""); //$NON-NLS-1$
		sb.append(MyBatis3FormattingUtilities
				.getAliasedActualColumnName(introspectedColumn));
		sb.append(' ');
		sb.append(operator);
		sb.append("\");"); //$NON-NLS-1$
		method.addBodyLine(sb.toString());
		method.addBodyLine("return (Criteria) this;"); //$NON-NLS-1$

		return method;
	}
	private Method getSingleValueMethod(IntrospectedColumn introspectedColumn,
										String nameFragment, String operator) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.addParameter(new Parameter(introspectedColumn
				.getFullyQualifiedJavaType(), "value")); //$NON-NLS-1$
		StringBuilder sb = new StringBuilder();
		sb.append(introspectedColumn.getJavaProperty());
		sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		sb.insert(0, "and"); //$NON-NLS-1$
		sb.append(nameFragment);
		method.setName(sb.toString());
		method.setReturnType(FullyQualifiedJavaType.getCriteriaInstance());
		sb.setLength(0);

		if (introspectedColumn.isJDBCDateColumn()) {
			sb.append("addCriterionForJDBCDate(\""); //$NON-NLS-1$
		} else if (introspectedColumn.isJDBCTimeColumn()) {
			sb.append("addCriterionForJDBCTime(\""); //$NON-NLS-1$
		} else if (stringHasValue(introspectedColumn
				.getTypeHandler())) {
			sb.append("add"); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
			sb.append("Criterion(\""); //$NON-NLS-1$
		} else {
			sb.append("addCriterion(\""); //$NON-NLS-1$
		}

		sb.append(MyBatis3FormattingUtilities
				.getAliasedActualColumnName(introspectedColumn));
		sb.append(' ');
		sb.append(operator);
		sb.append("\", "); //$NON-NLS-1$
		sb.append("value"); //$NON-NLS-1$
		sb.append(", \""); //$NON-NLS-1$
		sb.append(introspectedColumn.getJavaProperty());
		sb.append("\");"); //$NON-NLS-1$
		method.addBodyLine(sb.toString());
		method.addBodyLine("return (Criteria) this;"); //$NON-NLS-1$

		return method;
	}
}
