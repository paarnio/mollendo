<?xml version="1.0" encoding="utf-8"?>
<!-- trans_all_but_no_names_to_xml.xsl -->
<!-- target results...xml -->
<!-- https://www.freeformatter.com/xsl-transformer.html -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes" encoding="utf-8"/>
	<xsl:template match="/">
		<xsl:element name="studentSubmits">
			<xsl:attribute name="submitId">
			 <xsl:value-of select="./studentSubmits/@submitId"/>
			</xsl:attribute>
			<xsl:copy-of select="./studentSubmits/description"/>
			<xsl:copy-of select="./studentSubmits/referenceZip"/>
			<xsl:apply-templates select="./studentSubmits/student"/>
		</xsl:element>
	</xsl:template>
	<xsl:template match="student">
		<xsl:element name="student">
			<xsl:attribute name="studentId">
				<xsl:value-of select="./@studentId"/>
			</xsl:attribute>
			<xsl:copy-of select="./submitZip"/>
			<xsl:copy-of select="./exercise"/>
		</xsl:element>
	</xsl:template>
	
	
</xsl:stylesheet>