<?php
/**
 * get_tweets.php
 * Collect tweets from the Twitter streaming API
 * This must be run as a continuous background process
 * Latest copy of this code: http://140dev.com/free-twitter-api-source-code-library/
 * @author Adam Green <140dev@gmail.com>
 * @license GNU Public License
 * @version BETA 0.10
 */
require_once('./140dev_config.php');

// Extend the Phirehose class to capture tweets in the json_cache MySQL table
require_once(CODE_DIR . 'libraries/phirehose/phirehose.php');


class Consumer extends Phirehose
{
// A database connection is established at launch and kept open permanently
	public $oDB;
	public $filePtr;
	public $fileArray;
	public $screenNames;

	public function db_connect() {
		//require_once('./db_lib.php');
		//$this->oDB = new db;
		$this->screenNames = array('GIB89','BarackObama','JimCarrey','ladygaga','Oprah','ricky_martin','SrBachchan','JensonButton','lancearmstrong','bhogleharsha','kevinpp24','sachin_rt','Swannyg66','warne888','andresiniesta8','Cristiano','KAKA','luis16suarez','WayneRooney','SarahPalinUSA','SenJohnMcCain','shashitharoor','VP','KaleyCuoco');
		$this->fileArray["screen_name"] = "filePtr";
		foreach ($this->screenNames as $value) {
			$filePtr = fopen($value.".json","a") or die("error file open");
			error_log("-".$value."-");
			$this->fileArray[$value.""] = $filePtr;
		}

	}

  // This function is called automatically by the Phirehose class
  // when a new tweet is received with the JSON data in $status
	public function enqueueStatus($status) {
		$tweet_object = json_decode($status);	
		error_log("$status",0);
		$testStr="";
		if(isset($tweet_object->retweeted_status))
		{
			$rtObj = $tweet_object->retweeted_status;
			$user=($rtObj->user);
			$testStr=$user->screen_name."";
		}else if(isset($tweet_object->in_reply_to_screen_name)){
			$testStr=($tweet_object->in_reply_to_screen_name."");
		}else if(isset($tweet_object->user)){
			$user=($tweet_object->user);
			$testStr=$user->screen_name."";			
		}
		error_log($testStr);		
		if(array_key_exists($testStr."",$this->fileArray)){
			fwrite($this->fileArray[$testStr.""],$status);
		}
		
	//	$tweet_id = $tweet_object->id_str;
		//echo "$status";
	}
}



set_time_limit(0);
// Open a persistent connection to the Twitter streaming API
// Basic authentication (screen_name, password) is still used by this API
$stream = new Consumer("lokresh88", "21031988", Phirehose::METHOD_FILTER);

// Establish a MySQL database connection
$stream->db_connect();

// The keywords for tweet collection are entered here as an array
// More keywords can be added as array elements
// For example: array('recipe','food','cook','restaurant','great meal')
$stream->setFollow(array('52551600','14230524','19397785','40255499','145125358','23440052','16727535','78941611','171117515','135421739','45025729','87118217','155659213','213745334','285332860','65493023','19394188','24705126','325830217','95233955','813286','141088571'));

// Start collecting tweets
// Automatically call enqueueStatus($status) with each tweet's JSON data
$stream->consume();

?>