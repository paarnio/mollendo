<?xml version="1.0" encoding="utf-8"?>
<!-- trans_students_id_total_exid_points_to_csv_6.xsl -->
<!-- target results_U0_sub1_2018-01-08_remove_extra_newlines.xml -->
<!-- https://www.freeformatter.com/xsl-transformer.html -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" indent="no" encoding="utf-8"/>
	<xsl:template match="/">
			<xsl:apply-templates select="//student"/>
	</xsl:template>
	<xsl:template match="student">
		<xsl:value-of select="./@studentId"/>
		<xsl:text>;</xsl:text>
		<xsl:value-of select="sum(./exercise/pointsOfTestCases)"/>
		<xsl:text>;</xsl:text>
		<xsl:apply-templates select="./exercise"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<xsl:template match="exercise">
		<xsl:text> EXID:</xsl:text>
		<xsl:value-of select="./@exerciseId"/>
		<xsl:text> POINTS:</xsl:text>
		<xsl:value-of select="sum(./pointsOfTestCases)"/>
		<xsl:text> TCPS:</xsl:text>
		<xsl:for-each select="./pointsOfTestCases">
			<xsl:text> +</xsl:text>
			<xsl:value-of select="."/>			
		</xsl:for-each>
		<!--xsl:text>: RESULTS:</xsl:text>
		<xsl:for-each select="./resultsOfTestCases">
			<xsl:text> TCR:</xsl:text>
			<xsl:value-of select="."/>
		</xsl:for-each--> 
	</xsl:template>
</xsl:stylesheet>