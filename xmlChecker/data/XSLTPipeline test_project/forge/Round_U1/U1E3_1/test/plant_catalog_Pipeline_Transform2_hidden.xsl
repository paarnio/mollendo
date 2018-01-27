<?xml version="1.0"  encoding="utf-8"?>
<!-- Pipeline 2. transform selecting value of Common element of plant elements. method=text-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output encoding="utf-8" method="text"/>

	<xsl:template match="/">
		<xsl:apply-templates select="/Results"/>
	</xsl:template>

	<xsl:template match="Results">
		<xsl:value-of select="./Plant/Common"/>
	</xsl:template>
</xsl:stylesheet>