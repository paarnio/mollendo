<?xml version="1.0" encoding="UTF-8"?>
<!-- combine_max_step2_as_xml.xsl  -->
<!-- Maximum of the submit points -->
<!-- Source XML: using the result xml file of combine_sub_results_step1.xsl as a source-->
<!-- https://stackoverflow.com/questions/8702039/how-to-find-the-max-attribute-from-an-xml-document-using-xpath-1-0 -->


<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

  <xsl:template match="/">
	<xsl:apply-templates select="/combinedStudents"/>
  </xsl:template>

  <xsl:template match="combinedStudents">
	<xsl:element name="combinedStudents">
	
	<xsl:for-each select="./student">
		<xsl:choose>
				<!--xsl:when test="./@id=current()/preceding-sibling::student/@id">
					<xsl:text>&#xA;same:</xsl:text>
					<xsl:value-of select="."/>
				</xsl:when-->
		<xsl:when test="not(./@id=current()/preceding-sibling::student/@id)">
					<!--xsl:text>&#xA;</xsl:text><xsl:value-of select="./@id"/>
					<xsl:text>:</xsl:text>
					<xsl:value-of select="."/>
					<xsl:text>:MAX:</xsl:text-->
				
			<xsl:element name="student">
				<xsl:attribute name="studentId">
					<xsl:value-of select="./@id"/>
				</xsl:attribute>
				<xsl:element name="total">
					<xsl:attribute name="roundId">
						<xsl:value-of select="substring(./@submit,1,2)"/>
					</xsl:attribute>
				<!-- MAX of submits points -->
<xsl:value-of select="/combinedStudents/student[@id=current()/@id][not(number(.) &lt; preceding-sibling::student[@id=current()/@id]) and not(number(.) &lt; following-sibling::student[@id=current()/@id])]"/>
				</xsl:element>
			</xsl:element>			
		</xsl:when>
		</xsl:choose>
	</xsl:for-each>

	</xsl:element>
  </xsl:template>

   
</xsl:transform>