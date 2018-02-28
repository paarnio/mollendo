<?xml version="1.0" encoding="UTF-8"?>
<!-- combine_max_step2_as_csv.xsl  -->
<!-- Maximum of the submit points -->
<!-- Source XML: using the result xml file of combine_sub_results_step1.xsl as a source-->
<!-- https://stackoverflow.com/questions/8702039/how-to-find-the-max-attribute-from-an-xml-document-using-xpath-1-0 -->


<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="text" version="1.0" encoding="UTF-8" indent="no" />

  <xsl:template match="/">
	<xsl:apply-templates select="/combinedStudents"/>
  </xsl:template>

  <xsl:template match="combinedStudents">	
	<xsl:for-each select="./student">
		<xsl:choose>
		<xsl:when test="not(./@id=current()/preceding-sibling::student/@id)">
			<xsl:value-of select="./@id"/>
			<xsl:text>;</xsl:text>
			<xsl:value-of select="substring(./@submit,1,2)"/>
			<xsl:text>;</xsl:text>
<!-- MAX of submits points -->
<xsl:value-of select="/combinedStudents/student[@id=current()/@id][not(number(.) &lt; preceding-sibling::student[@id=current()/@id]) and not(number(.) &lt; following-sibling::student[@id=current()/@id])]"/>
			<xsl:text>&#xA;</xsl:text>	
		</xsl:when>
		</xsl:choose>
	</xsl:for-each>

  </xsl:template>

   
</xsl:transform>