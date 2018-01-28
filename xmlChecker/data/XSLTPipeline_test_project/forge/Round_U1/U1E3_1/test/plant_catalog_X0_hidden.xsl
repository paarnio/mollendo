<?xml version="1.0"  encoding="utf-8"?>
<!-- NOV2017 NO ERRORS TESTS: U1E3 X=0 ALL correct -->
<!-- U1E3: Checking version of plant_catalog.xsl -->
<!-- 2017-01-19 -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output encoding="utf-8" method="text"/>

	<xsl:template match="/" mode="kysymys1">
		<xsl:text>Kysymys 1 &#xA;</xsl:text>
		<xsl:text>Dokumentin juuri:&#xA;</xsl:text>
		<xsl:value-of select="/"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>

	<xsl:template match="/" mode="kysymys2">
		<xsl:text>Kysymys 2 &#xA;</xsl:text>
		<xsl:text>Dokumentin juurielementti:&#xA;</xsl:text>
		<xsl:for-each select="/CATALOG">
			<xsl:value-of select="@id"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/" mode="kysymys3">
		<xsl:text>Kysymys 3 &#xA;</xsl:text>
		<!-- Muutos: lapsielementit (oli lapset) -->
		<xsl:text>Kaikki juurielementin lapsi-elementit:&#xA;</xsl:text>
		<!-- OP1:(Plant lapsielementit) /CATALOG/Plant OP2:(kaikki lapsielementit) /CATALOG/* OP3:(kaikki lapset) /CATALOG/child::node()-->
		<xsl:for-each select="/CATALOG/*">
			<xsl:value-of select="@id"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/" mode="kysymys4">
		<xsl:text>Kysymys 4 &#xA;</xsl:text>
		<!-- Muutos: lapsenlapsi-elementit (oli lapsenlapset) -->
		<xsl:text>Kaikki juurielementin lapsenlapsi-elementit:&#xA;</xsl:text>
		<xsl:for-each select="/CATALOG/*/*">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>

	<xsl:template match="/" mode="kysymys5">
		<xsl:text>Kysymys 5 &#xA;</xsl:text>
		<xsl:text>Kolmannen Plant:n Common-elementin sisältö:&#xA;</xsl:text>
		<xsl:value-of select="/CATALOG/Plant[3]/Common/text()"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>

	<xsl:template match="/" mode="kysymys6">
		<xsl:text>Kysymys 6 &#xA;</xsl:text>
		<xsl:text>Viimeisen kasvin kasvitieteellinen nimi:&#xA;</xsl:text>
		<xsl:value-of select="/CATALOG/Plant[position()=last()]/Botanical/text()"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	
	<xsl:template match="/" mode="kysymys7">
		<xsl:text>Kysymys 7 &#xA;</xsl:text>
		<xsl:text>Kaikkien niiden kasvien Zone-elementit, joiden hinta  &lt;=3.20:&#xA;</xsl:text>
		<xsl:for-each select="/CATALOG/Plant[Price&lt;='3.20']/Zone">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<xsl:template match="/" mode="kysymys8">
		<xsl:text>Kysymys 8 &#xA;</xsl:text>
		<xsl:text>Valitsee kasvit, joiden hinta yli 6.60 ja jotka viihtyvät auringossa:&#xA;</xsl:text>
		<!--  -->
		<xsl:for-each select="/CATALOG/Plant[(Price&gt;'6.60') and (Light='Sun')]">
			<xsl:value-of select="Common/text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<xsl:template match="/" mode="kysymys9">
		<xsl:text>Kysymys 9 &#xA;</xsl:text>
		<!-- Muutos: Lisätty (Annual)  -->
		<xsl:text>Yksivuotisten (Annual) kasvien Zone-elementtejä edeltävät sisarelementit:&#xA;</xsl:text>
		<xsl:for-each select="/CATALOG/Plant[Zone='Annual']/Zone/preceding-sibling::*">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>
	
	<xsl:template match="/" mode="kysymys10">
		<xsl:text>Kysymys 10 &#xA;</xsl:text>
		<xsl:text>Yksivuotisten (Annual) kasvien Zone-elementtien kaikki sisarelementit:&#xA;</xsl:text>
		<xsl:for-each select="/CATALOG/Plant[Zone='Annual']/Zone/preceding-sibling::* | /CATALOG/Plant[Zone='Annual']/Zone/following-sibling::*">
			<xsl:value-of select="text()"/>
			<xsl:text>&#xA;</xsl:text>
		</xsl:for-each>
	</xsl:template>
	
	

	<!-- Älä muokkaa alla olevaa osiota tiedostosta mitenkään! -->

	<xsl:param name="kysymys">all</xsl:param>

	<xsl:template match="/">
		<xsl:text>&#xA;</xsl:text>
		<xsl:choose>
			<xsl:when test="$kysymys='1'">
				<xsl:apply-templates select="." mode="kysymys1"/>
			</xsl:when>
			<xsl:when test="$kysymys='2'">
				<xsl:apply-templates select="." mode="kysymys2"/>
			</xsl:when>
			<xsl:when test="$kysymys='3'">
				<xsl:apply-templates select="." mode="kysymys3"/>
			</xsl:when>
			<xsl:when test="$kysymys='4'">
				<xsl:apply-templates select="." mode="kysymys4"/>
			</xsl:when>
			<xsl:when test="$kysymys='5'">
				<xsl:apply-templates select="." mode="kysymys5"/>
			</xsl:when>
			<xsl:when test="$kysymys='6'">
				<xsl:apply-templates select="." mode="kysymys6"/>
			</xsl:when>
			<xsl:when test="$kysymys='7'">
				<xsl:apply-templates select="." mode="kysymys7"/>
			</xsl:when>
			<xsl:when test="$kysymys='8'">
				<xsl:apply-templates select="." mode="kysymys8"/>
			</xsl:when>
			<xsl:when test="$kysymys='9'">
				<xsl:apply-templates select="." mode="kysymys9"/>
			</xsl:when>
			<xsl:when test="$kysymys='10'">
				<xsl:apply-templates select="." mode="kysymys10"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="." mode="kysymys1"/>
				<xsl:apply-templates select="." mode="kysymys2"/>
				<xsl:apply-templates select="." mode="kysymys3"/>
				<xsl:apply-templates select="." mode="kysymys4"/>
				<xsl:apply-templates select="." mode="kysymys5"/>
				<xsl:apply-templates select="." mode="kysymys6"/>
				<xsl:apply-templates select="." mode="kysymys7"/>
				<xsl:apply-templates select="." mode="kysymys8"/>
				<xsl:apply-templates select="." mode="kysymys9"/>
				<xsl:apply-templates select="." mode="kysymys10"/>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:text>***</xsl:text>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>

</xsl:stylesheet>
