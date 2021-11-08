<h1>bWell</h1>
<p>Modular user-content based platform for people that constantly look for new ideas for improving their wellbeing and also for those who enjoy with sharing their knowledge</p>
<p>Application is divided on 4 theme modules:</p>
<p><b>eatWell, fitWell, restWell, thinkWell</b></p> 
<p>Each one contains standard CRUD functionalities. Entry creator is elastic and allows user to create well organized and customizable content.</p>
<p>Beside of standard functionalities service offers more advanced features. <b>Please find details at bottom of this document</b></p>
<h2>
Tech stack
</h2>
<h3>Backend:</h3>
<uL>
<li><h4>Java, Spring Boot</h4></li>
<li><h4>Jpa, Hibernate, PostgreSQL</h4></li>
<li><h4>Spring Security & Google OAuth2</h4></li>
<li><h4>Spoonacular API - powering eatWell module with nutrition data</h4></li> 
</uL>

<h3>Frontend https://github.com/DariuszOkonski/bwell-frontend </h3>
<uL>
<li><h4>ReactJS</h4></li>
<li><h4>Material-UI</h4></li>
<li><h4>Vanilla JS and small dose of custom HTML/CSS</h4></li>
<li><h4>Designed in Figma</h4></li>
</uL> 
<h2>I stage of development: <u>eatWell module</u></h2>

<p>
   Beside of basic of complex CRUD functionalities for all modules, in first stage of project there was a focus on eatWell module. So far there are implemented functionalities:
</p>
<ul>
    <li>Recipes' ingredients list are integrated with Spoonacular API - it provides detailed data about nutrients of hundreds thousands of ingredients</li>
    <li>Nutrients demand calculator that calculates demand based on user's body statistics and goal specified by user</li>
    <li>Diet plan page - where user can compare nutrients of meals from eatWell repository with his/her calculated demand and adjust portions and see what portions of given meal/ingredient should he intake to meet the goal</li>
</ul>

<h2>II stage of development: <u>security implementation and refactor</u></h2>

<p>
    Next step after finishing core functionalities and adding some more advanced logic based on third-party commercial API: <b>Spoonacular</b> next logical step before launching first beta tests was configuring security and authentication logic, because interactions with Spoonacular is limited.  
</p>
<p>So far there is accomplished basic Google OAuth2 based login system, that manages users capabilities across platform.</p>

<h3>Development plans</h3>

<ul>
    <li><s>Security</s> - basic OAuth2 based implementation done; next step is improving and enlarging with better and more complex security from malicious users</li>
    <li>Freemium based commercialisation model</li>
    <li>AI based guide for activities that can help in improving wellbeing sense on user situation described with some input form</li>
</ul>

