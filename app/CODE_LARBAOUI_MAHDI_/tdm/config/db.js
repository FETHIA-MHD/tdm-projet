/*const mysql = require('mysql')

const mysql = mysql.createPool({
  user: 'root',
  host: 'localhost',
  database: 'test-tdm',
  password: '',
  multipleStatements: true
})

module.exports=mysql*/

const Pool = require('pg').Pool

const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'test-tdm',
  password: 'root',
  port: 5432,
})

module.exports=pool 