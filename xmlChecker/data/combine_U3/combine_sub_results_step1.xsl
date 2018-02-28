<?xml version="1.0" encoding="UTF-8"?>
<!-- combine_sub_results_part1.xsl  -->
<!-- NOTE: line 61: comment out student name printing -->
<!-- Based on Book: XSLT p.161-164 -->
<!-- See also https://stackoverflow.com/questions/24735667/xalan-and-the-document-function -->

<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

  <xsl:template match="/">
	<xsl:element name="combinedStudents">
	   <xsl:call-template name="listStudentIds">
		</xsl:call-template>
	</xsl:element>
  </xsl:template>

 <xsl:template name="listStudentIds">
	<xsl:variable name="list-of-studids">
		<xsl:for-each select="document(/report/resultfile/@name, document(''))/studentSubmits/student/@studentId">
			<xsl:sort select="."/>
			<xsl:value-of select="."/><xsl:text> </xsl:text>
		</xsl:for-each>
	</xsl:variable>
	<!--xsl:value-of select="$list-of-studids"/-->
	
	<xsl:variable name="list-of-unique-studids">
		<xsl:call-template name="remove-duplicates">
		<xsl:with-param name="list-of-studids" select="$list-of-studids"/>
		<xsl:with-param name="last-id" select="''"/>			
		</xsl:call-template>
	</xsl:variable>
	<xsl:text>&#xA;</xsl:text>
	<!--xsl:value-of select="$list-of-unique-studids"/-->
	
	<xsl:call-template name="group-by-id">
		<xsl:with-param name="list-of-unique-studids" select="$list-of-unique-studids"/>		
	</xsl:call-template>
	
  </xsl:template>

  
  
  
	<xsl:template name="group-by-id">
		<xsl:param name="list-of-unique-studids"/>
			<xsl:variable name="next-id">
			<xsl:value-of select="substring-before($list-of-unique-studids, ' ')"/>
			</xsl:variable>	
			<xsl:variable name="remaining-ids">
			<xsl:value-of select="substring-after($list-of-unique-studids, ' ')"/>
			</xsl:variable>
			<xsl:for-each select="document(/report/resultfile/@name, document(''))/studentSubmits/student">
				<xsl:if test="@studentId=$next-id">
					<xsl:element name="student">
						<xsl:attribute name="id">
							<xsl:value-of select="@studentId"/>
						</xsl:attribute>
						<xsl:attribute name="submit">
							<xsl:value-of select="ancestor::studentSubmits/@submitId"/>
						</xsl:attribute>
						<!--xsl:value-of select="./surname"/><xsl:text>:</xsl:text--><!-- NOTE: line 61: comment out student name printing -->
						<xsl:value-of select="sum(./exercise/pointsOfTestCases)"/>
					</xsl:element>
					
				</xsl:if>
			</xsl:for-each>
			<xsl:if test="normalize-space($remaining-ids)">
				<xsl:call-template name="group-by-id">
					<xsl:with-param name="list-of-unique-studids" select="$remaining-ids"/>		
				</xsl:call-template>
			</xsl:if>
	</xsl:template>





  
   <xsl:template name="remove-duplicates">
	<xsl:param name="list-of-studids"/>
	<xsl:param name="last-id" select="''"/>	
	<xsl:variable name="next-id">
		<xsl:value-of select="substring-before($list-of-studids, ' ')"/>
	</xsl:variable>	
    <xsl:variable name="remaining-ids">
		<xsl:value-of select="substring-after($list-of-studids, ' ')"/>
	</xsl:variable>

	<xsl:choose>
		<xsl:when test="not(string-length(normalize-space($list-of-studids)))">
			<!-- empty -->
		</xsl:when>
		<xsl:when test="not($last-id=$next-id)">
			<xsl:value-of select="$next-id"/>
			<xsl:text> </xsl:text>
			<xsl:call-template name="remove-duplicates">
				<xsl:with-param name="list-of-studids" select="$remaining-ids"/>
				<xsl:with-param name="last-id" select="$next-id"/>			
			</xsl:call-template>
		</xsl:when>
		<xsl:when test="$last-id=$next-id">
			<xsl:call-template name="remove-duplicates">
				<xsl:with-param name="list-of-studids" select="$remaining-ids"/>
				<xsl:with-param name="last-id" select="$next-id"/>			
			</xsl:call-template>
		</xsl:when>
	</xsl:choose>
	
   </xsl:template>
   
   
   
   
   
</xsl:transform>