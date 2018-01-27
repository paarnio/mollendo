<?xml version="1.0"  encoding="utf-8"?>
<!-- Pipeline 1. transform selecting one plant element. method=xml-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output encoding="utf-8" method="xml"/>

	<xsl:template match="/">
		<xsl:element name="Results">
		<xsl:apply-templates select="/CATALOG"/>
		</xsl:element>
	</xsl:template>

	<xsl:template match="CATALOG">
		<xsl:copy-of select="./Plant[@id='02']"/>
	</xsl:template>
</xsl:stylesheet>
