<?xml version="1.0" encoding="UTF-8"?>
<!-- merge_xml_fraqs12.xsl  -->
<!-- NEW https://stackoverflow.com/questions/24735667/xalan-and-the-document-function -->
<!-- See OLD Book: XSLT p.149 ?? -->
<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

  <xsl:template match="/">
	<xsl:element name="combinedStudents">
	   <xsl:call-template name="docContent">
			<xsl:with-param name="docid" select="'1'"/><!-- test -->
			<xsl:with-param name="studs" select="document(/report/resultfile[position()=1]/@name, document(''))/studentSubmits/student"/>			
		</xsl:call-template>
	</xsl:element>
  </xsl:template>

  <xsl:template name="docContent">
	<xsl:param name="docid" select="'1'"/> 
	<xsl:param name="studs" select=""/>
	<xsl:text>&#xA;</xsl:text>
	<xsl:text>Current Level</xsl:text>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="$studs">
			<xsl:value-of select="position()"/>
			<xsl:text>:</xsl:text>
			<xsl:value-of select="./@studentId"/>
			<xsl:text>&#xA;</xsl:text>
	</xsl:for-each>
	<xsl:variable name="nexDoc" select="$docid+1"/>	
	<xsl:variable name="nexStuds" select="document(/report/resultfile[position()=$nexDoc]/@name, document(''))/studentSubmits/student"/>
	<xsl:text>Next Level</xsl:text>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="$nexStuds">
			<xsl:value-of select="position()"/>
			<xsl:text>:</xsl:text>
			<xsl:value-of select="./@studentId"/>
			<xsl:text>&#xA;</xsl:text>
	</xsl:for-each>
	
	<xsl:text>PROCESS BEGINS</xsl:text>
	<xsl:text>&#xA;</xsl:text>
	<xsl:for-each select="$studs">
			<xsl:variable name="same" select="$nexStuds[@studentId = current()/@studentId]"/>
			<xsl:value-of select="$same/@studentId"/>
			<xsl:text>&#xA;</xsl:text>
			<xsl:for-each select="$same/exercise">
			<xsl:value-of select="./@exerciseId"/>
			<xsl:text>,</xsl:text>
			</xsl:for-each>
			<xsl:text>&#xA;</xsl:text>
			
			<xsl:variable name="studX" select="."/>
			<xsl:element name="student">
				<xsl:attribute name="studentId">
				<xsl:value-of select="$studX/@studentId"/>
				</xsl:attribute>
				<!-- exercise LOOP -->
				<xsl:for-each select="./exercise">
				<xsl:variable name="exid" select="./@exerciseId"/>
				<xsl:element name="exercise">
					<xsl:attribute name="exerciseId">
					<xsl:value-of select="./@exerciseId"/>
					</xsl:attribute>
					<xsl:element name="totalPoints">
					<xsl:value-of select="sum(./pointsOfTestCases)"/>		  
					</xsl:element>
	
					<xsl:call-template name="newStudent">
						<xsl:with-param name="nexDoc" select="$docid+1"/><!-- test -->
						<xsl:with-param name="stuid" select="$studX/@studentId"/>
						<xsl:with-param name="exid" select="./@exerciseId"/>	
					</xsl:call-template>
					
				</xsl:element>
				</xsl:for-each>
			</xsl:element>
		</xsl:for-each>
  </xsl:template>
	
  <xsl:template name="newStudent">
	<xsl:param name="nexDoc" select="'1'"/> 
	<xsl:param name="stuid" select=""/>
	<xsl:param name="exid" select=""/>
	<xsl:variable name="nexStuds" select="document(/report/resultfile[position()=$nexDoc]/@name, document(''))/studentSubmits/student"/>
	<xsl:variable name="same" select="$nexStuds[@studentId = $stuid]"/>
	
				
		<xsl:for-each select="$same/exercise[@exerciseId=$exid]">
			<xsl:element name="totalPoints">
			<xsl:value-of select="sum(./pointsOfTestCases)"/>		  
			</xsl:element>
		</xsl:for-each>
		
		<!-- recursive call -->
		<xsl:if test="/report/resultfile[position()=$nexDoc+1]">		
		<xsl:call-template name="newStudent">
			<xsl:with-param name="nexDoc" select="$nexDoc+1"/>
			<xsl:with-param name="stuid" select="$stuid"/>
			<xsl:with-param name="exid" select="$exid"/>	
		</xsl:call-template>		
		</xsl:if>
   </xsl:template>
   
</xsl:transform>