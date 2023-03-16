<?PHP
session_start();

IF (!isset($_SESSION['UserSession']))
{
  header ('Location: ../../../../index.php');
  exit;
};

function compareExp($givenExp,$answerExp,$params)
{
  foreach( explode(';',$params) as $param_expr)
  {
      if(strlen($param_expr) == 0)
      { continue;
      };

      $pair = explode('=', $param_expr);
      $pair[0]=trim($pair[0]);
      $vars[]=$pair[0];
      $params_arr[$pair[0]]=trim($pair[1]);
  };
  $strStandardExp=$givenExp.' ';
  $strAnswerExp=$answerExp.' ';
  for ($i=0; $i<sizeof($vars); $i++)
  {
    $strStandardExp = ereg_replace('([^A-Za-z0-9]*)'.$vars[$i].'([^A-Za-z0-9]+)','\1'. $params_arr[$vars[$i]].'\2', $strStandardExp);
    $strAnswerExp = ereg_replace('([^A-Za-z0-9]*)'.$vars[$i].'([^A-Za-z0-9]+)','\1'. $params_arr[$vars[$i]].'\2', $strAnswerExp);
  };
  $result=false;
  @eval( '$result=(('.$strStandardExp.')==('.$strAnswerExp.'));');
  return $result;
};

function verifyAns ($question,$answer,$maxBa) //$maxBa-правильно, 0-не правильно
{
  $result=0;
  if ($answer=='')
    { $result=0;
    }
  elseif ($question[0]=='C')
    { $ans_A=array(1=>false,2=>false,3=>false,4=>false,5=>false);
      $answer=str_replace('ans_A','$ans_A',$answer);
      eval($answer);
      $tans_A=array(1=>false,2=>false,3=>false,4=>false,5=>false);
      for ($i=1;$i<=count($question['true_answer']);$i++)
      {
        $tans_A[$question['true_answer'][$i]]=true;
      };
      $ba=true;
      for ($i=1;$i<=5;$i++)
      {
         $ba=($ba and !($ans_A[$i] xor $tans_A[$i]));
      };
      if ($ba) { $result=$maxBa; };
    }
  elseif ($question[0]=='S')
    { $ans_A=array(1=>false,2=>false,3=>false,4=>false,5=>false);
      $ba=false;
      $answer=str_replace('ans_A['.$question['true_answer'][1].']','$ba',$answer);
      $answer=str_replace('ans_A','$ans_A',$answer);
      eval($answer);
      if ($ba) { $result=$maxBa; };
    }
  elseif ($question[0]=='I')
    { if (is_numeric($answer))
      {
        $t=intval($answer);
        if ($t==intval($question['answer'][1]))
          { $result=$maxBa;
          };
      };
    }
  elseif ($question[0]=='F')
    { $answer=str_replace(',','.',$answer);
      if (is_numeric($answer))
      {
        $t=floatval($answer);
        if ($question['answer'][2]=='%')
        {
          $num=floatval($question['answer'][1]);
          $acu=floatval($question['answer'][3]);
          if (((100-$acu)/100*$num<$t) and ($t<(100+$acu)/100*$num))
          { $result=$maxBa;
          };
        };
      };
    }
  elseif ($question[0]=='T')
    { if (substr($question['answer'][1],0,1)=='{' && substr($question['answer'][1],-1)=='}')
      { $Q=explode('|',substr($question['answer'][1],1,-1));
        foreach( $Q as $val)
        { if ($answer==$val)
          { $result=$maxBa;
            break;
          };
        };
      }
      else
      {
        if ($answer==$question['answer'][1])
        { $result=$maxBa;
        };
      };

    }
  elseif ($question[0]=='E')
    { $t=substr($question['answer'][1],strpos($question['answer'][1],'=')+1);
      $ba=true;
      for ($i=1;$i<=count($question['true_answer']);$i++)
      {
          $ba=($ba and compareExp($t,$answer,$question['answer'][$question['true_answer'][$i]]));
      };
      if ($ba) { $result=$maxBa; };
    }
  elseif (($question[0]=='L') || ($question[0]=='D'))
    { $tans_A=array(1=>10,2=>10,3=>10,4=>10,5=>10);
      $answer=str_replace('ans_A','$tans_A',$answer);
      eval($answer);
      $ans_A=$tans_A;
      for ($k=1;$k<=count($question['true_answer']);$k++)
      { $ans_A[$k]=$tans_A[$question['key'][$k]];
      };
//      print_r($tans_A);
//      print_r($ans_A);
      $ba=true;
      for ($i=1;$i<=count($ans_A);$i++)
      {
          $t=($i-1)*2;
          $ba=($ba and (($ans_A[$t/2+1]==$t+1) or ($ans_A[$t/2+1]==11)));
      };
      if ($ba) { $result=$maxBa; };
    }
  elseif ($question[0]=='M')
    {
      $ans_A=array(1=>0,2=>0,3=>0,4=>0,5=>0);
      $answer=str_replace('ans_A','$ans_A',$answer);
      eval($answer);
      $ba=true;
      for ($i=1;$i<=count($question['true_answer']);$i++)
      {
          $ba=($ba and ($ans_A[$question['true_answer'][$i]]>0) and (($question['key'][$question['true_answer'][$i]][$ans_A[$question['true_answer'][$i]]-1])==0));
      };
      if ($ba) { $result=$maxBa; };
    }
  else if (($question[0]=='W') || ($question[0]=='V'))
    {
      $ans_A=array();
      $answer=str_replace('ans_A','$ans_A',$answer);
      eval($answer);
      $ba=true;
      $wBal=0;
      for($i=1;$i<=count($question['answer']);$i++)
      {
        $r1=false;
        foreach($question['answer'][$i] as $ans)
        {
          $r1=($r1 || $ans==$ans_A[$i]);
        };
        $ba=$ba && $r1;
        if ($r1)
        { $wBal=$wBal+$question['balls'][$i];
        };
      };
      if ($question[0]=='W')
      { $result=$wBal;
      }
      elseif ($question[0]=='V' && $ba)
      { $result=$maxBa;
      };
    };
  return $result;
};

function userQuestionW($question,$answer)
{
  $inpL='<input type="text" disabled="disabled" value="';
  $inpM='" size="';
  $inpR='" />';
  $str=$question[1];
  $ans_A=array_fill(1,count($question['answer']),'');
  $answer=str_replace('ans_A','$ans_A',$answer);
  eval($answer);
  for ($i=1;$i<=count($question['answer']);$i++)
  {
   $j=strpos($str,'{W'.$i.'}');
   if ($j>0)
   {
     $str=substr($str,0,$j).$inpL.$ans_A[$i].$inpM.strlen($ans_A[$i]).$inpR.substr($str,$j+strlen('{W'.$i.'}'));
   };
  };
  return '<p>'.$str.'</p>';
};

function userQuestionM($question,$answer)
{
  $selL='<select disabled="disabled">';
  $selR='</select>';
  $optL='<option ';
  $optR='>';
  $tmp='';
  $str=$question[1];
  $ans_A=array(1=>0,2=>0,3=>0,4=>0,5=>0);
  $answer=str_replace('ans_A','$ans_A',$answer);
  eval($answer);
  for ($i=1;$i<=count($question['answer']);$i++)
  {
   $j=strpos($str,'{L'.$i.'}');
   if ($j>0)
   {
     $tmp=$selL;
     $tmp.="\n".$optL;
     if($ans_A[$i]==0)
     {
       $tmp.='selected="selected"';
     };
     $tmp.=$optR.'&nbsp;';
     for ($n=1;$n<=count($question['answer'][$i]);$n++)
     {
       $tmp.="\n".$optL;
       if($ans_A[$i]==$n)
       {
         $tmp.='selected="selected"';
       };
       $tmp.=$optR.$question['answer'][$i][$question['key'][$i][$n-1]];
     };
     $tmp.=$selR;
     $str=substr($str,0,$j).$tmp.substr($str,$j+strlen('{L'.$i.'}'));
   };
  };
  return '<p>'.$str.'</p>';
};

function userQuestion($question,$answer)
{
  if($question[0]=='W' || $question[0]=='V')
  {
    return userQuestionW($question,$answer);
  }
  else if($question[0]=='M')
  {
    return userQuestionM($question,$answer);
  }
  else
  {
    return $question[1];
  }
};

function userAnswerI($question,$answer)
{
   return '&nbsp; Ответ: &nbsp; '.$question['answer'][3].
          '<input type="text"  size="'.(ceil(strlen($question['answer'][1])/5)*5).
          '" disabled="disabled" value="'.$answer.'" /> '.$question['answer'][2];
};

function userAnswerF($question,$answer)
{
   return '&nbsp; Ответ: &nbsp; '.$question['answer'][5].
          '<input type="text"  size="'.(ceil(strlen($question['answer'][1])/5)*5).
          '" disabled="disabled" value="'.$answer.'" /> '.$question['answer'][4];
};

function userAnswerE($question,$answer)
{
  $e=explode('=',$question['answer'][1]);
  return '&nbsp; Ответ: &nbsp; '.$e[0].
         '=<input type="text" size="25" disabled="disabled" value="'.$answer.'" />';
};

function userAnswerT($question,$answer)
{
  return '&nbsp; Ответ: &nbsp; <input type="text" disabled="disabled" value="'.$answer.
         '" />';
};

function userAnswerC($question,$answer)
{
  $ans_A=array(1=>false,2=>false,3=>false,4=>false,5=>false);
  $answer=str_replace('ans_A','$ans_A',$answer);
  eval($answer);
  $str='&nbsp; Варианты ответов:';
  for ($i=1;$i<=5;$i++)
  {
    if($question['answer'][$i]!='')
    {
      $str.='<br/><input type="checkbox" disabled="disabled" '.
            ($ans_A[$i]?'checked="checked" ':'').'/> ';
      if($question['answer_type'][$i]=='I')
      { $str.='<img src="'.$question['answer'][$i].'" alt="'.$question['answer'][$i].'" />';
      }
      else
      { $str.=$question['answer'][$i];
      };
    };
  };
  return $str;
};

function userAnswerS($question,$answer)
{
  $ans_A=array(1=>false,2=>false,3=>false,4=>false,5=>false);
  $answer=str_replace('ans_A','$ans_A',$answer);
  eval($answer);
  $str='&nbsp; Варианты ответов:';
  for ($i=1;$i<=5;$i++)
  {
    if($question['answer'][$i]!='')
    {
      $str.='<br/><input type="radio" disabled="disabled" '.
            ($ans_A[$i]?'checked="checked" ':'').'/> ';
      if($question['answer_type'][$i]=='I')
      { $str.='<img src="'.$question['answer'][$i].'" alt="'.$question['answer'][$i].'" />';
      }
      else
      { $str.=$question['answer'][$i];
      };
    };
  };
  return $str;
};

function userAnswerL($question,$answer)
{
  $tans_A=array(1=>10,2=>10,3=>10,4=>10,5=>10);
  $answer=str_replace('ans_A','$tans_A',$answer);
  eval($answer);
  $str='&nbsp; Ответ: &nbsp;<table>';
  for ($k=1;$k<=count($question['true_answer']);$k++)
  {
   $str.='<tr><td>'.$question['answer'][$k][0];
   $str.='</td><td>'."\n".'<select disabled="disabled">'."\n";
   $str.='<option '.($tans_A[$question['key'][$k]]==10?'selected="selected" ':'').'>&nbsp;'."\n";
   for($i=1;$i<=5;$i++)
   { $str.='<option '.(($tans_A[$question['key'][$k]]==$i*2-1)?'selected="selected" ':'').'>'.$question['answer'][$i][1]."\n";
   };
   $str.='</select>'."\n".'</td></tr>';
  };

  $str.='</table>';
  return $str;
};

function userAnswer($question,$answer)
{
  if($question[0]=='I')
  {
    return userAnswerI($question,$answer);
  }
  else if($question[0]=='F')
  {
    return userAnswerF($question,$answer);
  }
  else if($question[0]=='E')
  {
    return userAnswerE($question,$answer);
  }
  else if($question[0]=='T')
  {
    return userAnswerT($question,$answer);
  }
  else if($question[0]=='W')
  {
    return '';//userAnswerW($question,$answer);
  }
  else if($question[0]=='M')
  {
    return '';
  }
  else if($question[0]=='C')
  {
    return userAnswerC($question,$answer);
  }
  else if($question[0]=='S')
  {
    return userAnswerS($question,$answer);
  }
  else if($question[0]=='L')
  {
    return userAnswerL($question,$answer);
  }
};

function getResults()
{
  $us=$_SESSION['UserSession'];
  $test=$_SESSION['UserSession']['tests'][$_POST['id_test']];
  $N=0;
  $bC=0;
  $bN=0;
  $testCount=$test['testCount'];
  $lastAnswer=$_POST['lastAnswer'];
  $EOL='
';

  for ($i=1;$i<=$testCount;$i++)
  {
    $bC=$bC+$test['question'][$i][5];
  };
  $table=array();
  for ($i=1;$i<=$lastAnswer;$i++)
  {
    $table[$i]=array();
    $t= verifyAns($test['question'][$i],$_POST['ans_'.$i],$test['question'][$i][5]);
    if ($t>0)
    { $N=$N+1;
      $bN=$bN+$t;
      $table[$i][1]='#00FF00';
      $table[$i][4]=strval($t);
    }
    else
    { $table[$i][1]='#FF0000';
      $table[$i][4]='0';
    };
    if ($test['question'][$i][0]=='W')
    { $table[$i][2]='специальный';
    }
    elseif ($test['question'][$i][5]==2)
    { $table[$i][2]='простой';
    }
    elseif ($test['question'][$i][5]==4)
    { $table[$i][2]='средний';
    }
    elseif ($test['question'][$i][5]==6)
    { $table[$i][2]='сложный';
    };
    $table[$i][3]=userQuestion($test['question'][$i],$_POST['ans_'.$i]);
    $table[$i][5]=userAnswer($test['question'][$i],$_POST['ans_'.$i]);
  };

  $head='      <br /> &nbsp; Время начала теста : <FONT size=+1><b>'.date("H:i:s",$test['startTime']).' &nbsp;&nbsp;&nbsp; '.date("d.m.Y",$test['startTime']).'</b></FONT> '.$EOL;
  $head.='      <br /> &nbsp; Вы правильно ответили на <FONT size=+1><b>'.$N.'</b></FONT> ';
  if ((5<=$N) and ($N<20))
  { $head.='вопросов';
  }
  else
  {
    $ts=substr(strval($N),-1);
    if ($ts=='1')
    { $head.='вопрос';
    }
    elseif (($ts=='2') or ($ts=='3') or ($ts=='4'))
    { $head.='вопросa';
    }
    else
    { $head.='вопросов';
    };
  };
  $head.=' из <font size=+1><b>'.$testCount.'</b></font>';
  if ($N==$testCount)
  { $is_right='Y';
  }
  else
  { $is_right='N';
  };
  $elapsed_time=gmdate("H:i:s",$test["endTime"]-$test["startTime"]);
  $head.='      <br /> &nbsp; Время прохождения теста: <font size=+1><b>'.$elapsed_time.'</b></font>'.$EOL;
//vm  $head.='      <br /> &nbsp; Вы набрали  <font size=+1><b>'.$bN.'</b></font> ';
//vm  if ((5<=$bN) and ($bN<20))
//vm  { $head.='очков';
//vm  }
//vm  else
//vm  {
//vm    $ts=substr(strval($bN),-1);
//vm    if ($ts=='1')
//vm    { $head.='очко';
//vm    }
//vm    elseif (($ts=='2') or ($ts=='3') or ($ts=='4'))
//vm    { $head.='очка';
//vm    }
//vm    else
//vm    { $head.='очков';
//vm    };
//vm  };
  $head.=' из <font size="+1"><b>'.$bC.'</b></font> возможных';
  if ($lastAnswer==$testCount)
  { $grade=round($bN/$bC*10000)/100;
    $head.='      <br /> &nbsp; Набрано <font size="+1"><b>'.strVal($grade).'%</b> от максимального балла</font>';
  }
  else
  { $head.='      <br /> &nbsp; Набрано 0 баллов: незавершённое прохождение, <font size="+1"><b>'.strVal(round($bN/$bC*10000)/100).'%</b>  от максимального балла.</font>';
    $head.='      <br /> <font color="red">&nbsp; Для получения ненулевого балла необходимо ответить на все вопросы теста </font>'.$EOL;
    $grade=0;
  };

  $report='
    <script language="JavaScript" type="text/javascript">
      function displayRes(name)
      {
        var obj=document.getElementById(name);
        if (obj.style.display=="none")
        {
          obj.style.display="";
        }
        else
        {
          obj.style.display="none";
        };
      };
    </script>
      <br /><br />
      <table border="2" width="95%">
      <tr>
        <td align="center">
         N
        </td>
        <td align="center">
         Уровень сложности
        </td>
        <td align="center">
         Текст вопроса
        </td>
        <td align="center">
         Очки
        </td>
      </tr>'.$EOL;
  for ($i=1;$i<=$lastAnswer;$i++)
  {
    $report.='
      <tr>
        <td align="center" bgcolor="'.$table[$i][1].'">
         '.$i.'
        </td>
        <td align="center" >
         '.$table[$i][2].'
        </td>
        <td>
         <div onclick="displayRes(\'a'.$i.'\')" title="подробный ответ" style="cursor:pointer">
          '.$table[$i][3].'
         </div>
         <div id="a'.$i.'" style="display:none"><hr width="100%"/>
          '.$table[$i][5].'
         </div>
        </td>
        <td align="center" bgcolor="'.$table[$i][1].'">
         '.$table[$i][4].'
        </td>
      </tr>'.$EOL;
  };
  $report.='      </table>'.$EOL;

  return array($head,$report,$is_right,$grade,$elapsed_time);
};

// Начало формирования документа!
if (isset($_SESSION['lng']))
{ $lng_id=$_SESSION['lng'];
};
include '../Template/language/problem_lng.php';
$TitlePrefix='';
$Title=$lng_title;
include '../Template/Top.php';
include '../Template/OpenDB.php';

if (isset($_POST["id_test"]) and isset($_SESSION['UserSession']['tests'])
    and isset($_SESSION['UserSession']['tests'][$_POST['id_test']])
   )
{
  $id_test=$_POST['id_test'];
  $end=time();
  $_SESSION['UserSession']['tests'][$id_test]['endTime']=$end;
  $us = $_SESSION['UserSession'];
  $id_user = $us['id_user'];
  $id_group = $us['id_group'];

  $orderN=$us['tests'][$id_test]['orderN'];

?>
    <table align="center" cellpadding="3" cellspacing="2" width="70%">
      <tr>
        <td class="bgButton" align=center width="33%" nowrap><a href="../../../problem.php?num=<?=$orderN?>" class="abutton"><?=$lng_Task.' '.$orderN?></a></td>
        <td class="bgButton" align=center width="33%" nowrap><a href="../../../list.php" class="abutton"><?=$lng_TaskList?></a></td>
        <td class="bgButton" align=center width="33%" nowrap><a href="../../../../index.php" class="abutton"><?=$lng_Exit?></a></td>
      </tr>
    </table>
<?

  unset($id_list,$enddatetime,$duration);

  $query='SELECT lc.id_list, lc.duration '.
         'FROM groups AS g '.
         'INNER JOIN list_constraint AS lc ON g.id_list=lc.id_list '.
         'WHERE g.id_group='.$id_group.' '.
         'LIMIT 1';
  $result = mysql_query($query, $link);
  if(mysql_num_rows($result) > 0)
  { list($id_list,$duration) = mysql_fetch_row($result);
    $query = 'SELECT log_date '.
             'FROM user_log_list '.
             'WHERE id_user='.$id_user.' AND id_list='.$id_list.' '.
             'LIMIT 1';
    $result = mysql_query($query, $link);
    if(mysql_num_rows($result)>0){
      list($log_time)=mysql_fetch_row($result);
    }
  }
  else
  {
    $query = 'SELECT enddatetime '.
             'FROM group_constraint '.
             'WHERE id_group = '.$id_group.' '.
             'LIMIT 1';
    $result = mysql_query($query, $link);
    if(mysql_num_rows($result) > 0)
    { list($enddatetime) = mysql_fetch_row($result);
    };
  };

  if((isset($duration) && ($end > (dbToTime($log_time)+dbTimeToTime($duration)))) ||
     (isset($enddatetime) && ($end > dbToTime($enddatetime)))
    )
  {
    echo '      <center><font size=+1 color="red"><b>Регистрация результатов закончена!</b></font></center>'."\n";
    echo '      <script language="JavaScript" type="text/javascript">alert("Результаты не зарегистрированы!");</script>'."\n";
  }
  else
  {
    $id_problem = $us['tests'][$id_test]['id_problem'];
    list($test_result1,$test_result2,$is_right,$grade,$elapsed_time)= getResults();

    if ($us['group'] != 'admin' && $us['group'] != 'staff')
    { $query='insert into answers (id_user, remote_addr, is_right, elapsed_time, id_problem, grade, ans_date) '.
             'values ('.$id_user.', \''.$_SERVER['REMOTE_ADDR'].'\', \''.$is_right.'\', \''.$elapsed_time.'\', '.$id_problem.', '.$grade.',\''.date("YmdHis",time()).'\')';
      mysql_query($query, $link);
      if($id_record = mysql_insert_id($link))
      {
        $query = 'insert into records (id_record, otvet_expr) values ('.$id_record.', \''.str_replace('\'','\\\'',$test_result1.$test_result2).'\')';
        mysql_query($query, $link);
    	$query = 'INSERT INTO `user_log` (id_user, ip, action, id_problem, id_record) '.
	    		 'VALUES ('.$id_user.', \''.$_SERVER['REMOTE_ADDR'].'\', \'answer\', '.$id_problem.', '.$id_record.')';
    	@mysql_query($query,$link);
		
      };

    };

    $query='SELECT show_res
            FROM p_Test
            WHERE id_problem = '.$id_problem;
    $result = mysql_query($query, $link);

    list($show_result) = mysql_fetch_row($result);
    if($show_result==1)
    { echo $test_result1.$test_result2;
    }
    else
    { echo $test_result1;
    };
  };
  unset($_SESSION['UserSession']['tests'][$id_test]);
}
else
{
   $path_parts = pathinfo($_SERVER['PHP_SELF']);
   include substr($path_parts['basename'],0,strpos($path_parts['basename'],'.')).'_param.php';
   
   $testCount=count($question);
   if ($question_order==1)
   {
     for ($i=1;$i<=$testCount;$i++)
     {
       $r=rand(1,$testCount);
       $tq=$question[$i];
       $question[$i]=$question[$r];
       $question[$r]=$tq;
     };
     $r=$testCount;
     $i=1;
     $c2=0;
     $c4=0;
     $c6=0;
     $cS=0;
     while ($i<=$r)
     {
       if ($question[$i][0]=='W')
       { if ($cS==$question_count_S)
         { $tq=$question[$i];
           $question[$i]=$question[$r];
           $question[$r]=$tq;
           $r=$r-1;
         }
         else
         {
           $cS=$cS+1;
           $i=$i+1;
         };
       }
       else if ($question[$i][5] == 2)
       { if ($c2==$question_count_2)
         { $tq=$question[$i];
           $question[$i]=$question[$r];
           $question[$r]=$tq;
           $r=$r-1;
         }
         else
         {
           $c2=$c2+1;
           $i=$i+1;
         };
       }
       else if ($question[$i][5] == 4)
       { if ($c4==$question_count_4)
         { $tq=$question[$i];
           $question[$i]=$question[$r];
           $question[$r]=$tq;
           $r=$r-1;
         }
         else
         {
           $c4=$c4+1;
           $i=$i+1;
         };
       }
       else if ($question[$i][5] == 6)
       { if ($c6==$question_count_6)
         { $tq=$question[$i];
           $question[$i]=$question[$r];
           $question[$r]=$tq;
           $r=$r-1;
         }
         else
         {
           $c6=$c6+1;
           $i=$i+1;
         };
       };
    };
    $testCount=$c2+$c4+$c6+$cS;
   };

   list($usec, $sec) = explode(' ', microtime());
   srand((float) $sec + ((float) $usec * 100000));
   $id_test = 't'.rand();
   while(isset($_SESSION['UserSession']['tests'][$id_test]))
   {
     $id_test = 't'.rand();
   };
   if(isset($_SESSION['UserSession']['id_problem_test']))
   {
     $_SESSION['UserSession']['tests'][$id_test]['id_problem']=$_SESSION['UserSession']['id_problem_test'];
   }
   elseif(isset($_SESSION['UserSession']['id_problem']))
   {
     $_SESSION['UserSession']['tests'][$id_test]['id_problem']=$_SESSION['UserSession']['id_problem'];
   };
   $_SESSION['UserSession']['tests'][$id_test]['orderN']=$_SESSION['UserSession']['orderN'];

   $paramStr='   var order_type='.$question_order.";\n";
   $formStr='<form name="answers" action="'.$_SERVER['PHP_SELF'].'" method="post" accept-charset="utf-8">'."\n";
   $formStr.='  <input type="hidden" name="id_test" value="'.$id_test.'" />'."\n";
   $formStr.='  <input type="hidden" name="lastAnswer" value="0" />'."\n";
   for($i=1;$i<=$testCount;$i++)
   {
    if ($question[$i][0]=='W' || $question[$i][0]=='V')
    {
      $ans_str='   answer['.$i.']=new Array(';
      $question[$i]['answer']=array();
      $question[$i]['balls']=array();
      $t1='';
      for($n=0,$z=1;$n<strlen($question[$i][1]);$z++)
      {
        $k=strpos($question[$i][1],'{',$n);
        if($k>=$n)
        {
          $m=strpos($question[$i][1],'}',$k);
          if($m>0)
          {
            $t1.=substr($question[$i][1],$n,$k-$n).'{W'.$z.'}';
            $question[$i]['answer'][$z]=explode('|',substr($question[$i][1],$k+1,$m-$k-1));
            $tmpA=$question[$i]['answer'][$z];
            $LW=strlen($tmpA[0]);
            for($j=1;$j<count($tmpA);$j++)
            { $LW=($LW<strlen($tmpA[$j])?strlen($tmpA[$j]):$LW);
            };
            if($z>1) { $ans_str.=','; };
            $ans_str.=ceil($LW/5)*5;
            $n=$m+1;
            if(substr($question[$i][1],$n,1)==':')
            {
              $n++;
              for($k=1;is_numeric(substr($question[$i][1],$n,$k))&&($n+$k<=strlen($question[$i][1]));$k++)
              {};
              $question[$i]['balls'][$z]=floatval(substr($question[$i][1],$n,$k-1));
              $n=$n+$k-1;
            };
          }
          else
          {
            $t1.=substr($question[$i][1],$n);
            $n=strlen($question[$i][1]);
          };
        }
        else
        {
          $t1.=substr($question[$i][1],$n);
          $n=strlen($question[$i][1]);
        };
      };
      $question[$i][1]=$t1;
      if($question[$i][0]=='W') {$question[$i][5]=array_sum($question[$i]['balls']);}
      $paramStr.='   question['.$i.']=new Array(\''.$question[$i][0].'\',\''.str_replace('/','\/',$question[$i][1]).'\',\''.$question[$i][2].'\');'."\n";
      $formStr.='  <input type="hidden" name="ans_'.$i.'" value="" />'."\n";
//      $formStr.='  <input type="text" name="ans_'.$i.'" value="" />'."\n";
      $ans_str.=");\n";
      $ans_type_str='   answer_type['.$i.']=new Array();'."\n";
      $paramStr.=$ans_str;
      $paramStr.=$ans_type_str;
    }
    else
    {
      $paramStr.='   question['.$i.']=new Array(\''.$question[$i][0].'\',\''.str_replace('/','\/',$question[$i][1]).'\',\''.$question[$i][2].'\');'."\n";
      $formStr.='  <input type="hidden" name="ans_'.$i.'" value="" />'."\n";
//      $formStr.='  <input type="text" name="ans_'.$i.'" value="" />'."\n";
    };
    if ($question[$i][0]=='I')
    {
      $ans_str='   answer['.$i.']=new Array(\''.(ceil(strlen($question[$i]['answer'][1])/5)*5).'\',\''.str_replace('/','\/',$question[$i]['answer'][2]).'\',\''.str_replace('/','\/',$question[$i]['answer'][3]).'\');'."\n";
      $ans_type_str='   answer_type['.$i.']=new Array();'."\n";
      $paramStr.=$ans_str;
      $paramStr.=$ans_type_str;
    }
    else if ($question[$i][0]=='F')
    {
      $ans_str='   answer['.$i.']=new Array(\''.(ceil(strlen($question[$i]['answer'][1])/5)*5).'\',\''.str_replace('/','\/',$question[$i]['answer'][4]).'\',\''.str_replace('/','\/',$question[$i]['answer'][5]).'\');'."\n";
      $ans_type_str='   answer_type['.$i.']=new Array();'."\n";
      $paramStr.=$ans_str;
      $paramStr.=$ans_type_str;
    }
    else if ($question[$i][0]=='T')
    {
      $ans_str='   answer['.$i.']=new Array();'."\n";
      $ans_type_str='   answer_type['.$i.']=new Array();'."\n";
      $paramStr.=$ans_str;
      $paramStr.=$ans_type_str;
    }
    else if ($question[$i][0]=='E')
    {
      $k= strpos($question[$i]['answer'][1],"=")+1;
      $ans_str='   answer['.$i.']=\''.substr($question[$i]['answer'][1],0,$k).'\';'."\n";
      $ans_type_str='   answer_type['.$i.']=new Array();'."\n";
      $paramStr.=$ans_str;
      $paramStr.=$ans_type_str;
    }
    else if ($question[$i][0]=='C')
    {
      $ans_str='   answer['.$i.']=new Array(';
      $ans_type_str='   answer_type['.$i.']=new Array(';
      for ($k=1;$k<count($question[$i]['answer']);$k++)
      { $ans_str.='\''.str_replace('/','\/',$question[$i]['answer'][$k]).'\',';
        $ans_type_str.='\''.$question[$i]['answer_type'][$k].'\',';
      };
      if (count($question[$i]['answer'])>0 )
      { $ans_str.='\''.str_replace('/','\/',$question[$i]['answer'][$k]).'\'';
        $ans_type_str.='\''.$question[$i]['answer_type'][$k].'\'';
      };
      $ans_str.=");\n";
      $ans_type_str.=");\n";
      $paramStr.=$ans_str;
      $paramStr.=$ans_type_str;
    }
    else if ($question[$i][0]=='S')
    {
      $ans_str='   answer['.$i.']=new Array(';
      $ans_type_str='   answer_type['.$i.']=new Array(';
      for ($k=1;$k<count($question[$i]['answer']);$k++)
      { $ans_str.='\''.str_replace('/','\/',$question[$i]['answer'][$k]).'\',';
        $ans_type_str.='\''.$question[$i]['answer_type'][$k].'\',';
      };
      if (count($question[$i]['answer'])>0 )
      { $ans_str.='\''.str_replace('/','\/',$question[$i]['answer'][$k]).'\'';
        $ans_type_str.='\''.$question[$i]['answer_type'][$k].'\'';
      };
      $ans_str.=");\n";
      $ans_type_str.=");\n";
      $paramStr.=$ans_str;
      $paramStr.=$ans_type_str;
    }
    else if ($question[$i][0]=='L')
    {
      $ans_str='   answer['.$i.']=new Array(';
      $ans_type_str='   answer_type['.$i.']=new Array();'."\n";
      for ($k=1;$k<=count($question[$i]['true_answer']);$k++)
      {
        $question[$i]['key'][$k]=$k;
      };
      for ($k=1;$k<=count($question[$i]['true_answer']);$k++)
      {
        $r=rand(1,count($question[$i]['true_answer']));
        $qt=$question[$i]['true_answer'][$k];
        $question[$i]['true_answer'][$k]=$question[$i]['true_answer'][$r];
        $question[$i]['true_answer'][$r]=$qt;
        $qt=$question[$i]['key'][$k];
        $question[$i]['key'][$k]=$question[$i]['key'][$r];
        $question[$i]['key'][$r]=$qt;
      };
      for ($k=1;$k<count($question[$i]['true_answer']);$k++)
      {
        $t1=$question[$i]['answer'][$question[$i]['true_answer'][$k]][0];
        $t2=$question[$i]['answer'][$question[$i]['true_answer'][$question[$i]['key'][$k]]][1];
        $t1=str_replace('/','\/',$t1);
        $t2=str_replace('/','\/',$t2);
        $ans_str.='\''.$t1.'\',\''.$t2.'\',';
      };
      if (count($question[$i]['true_answer'])>0 )
      {
        $t1=$question[$i]['answer'][$question[$i]['true_answer'][$k]][0];
        $t2=$question[$i]['answer'][$question[$i]['true_answer'][$question[$i]['key'][$k]]][1];
        $t1=str_replace('/','\/',$t1);
        $t2=str_replace('/','\/',$t2);
        $ans_str.='\''.$t1.'\',\''.$t2.'\'';
      };
      $ans_str.=");\n";
      $paramStr.=$ans_str;
      $paramStr.=$ans_type_str;
    }
    else if ($question[$i][0]=='M')
    {
      $ans_str='   answer['.$i.']=new Array();'."\n";
      $ans_type_str='   answer_type['.$i.']=new Array();'."\n";
      for ($k=1;$k<=count($question[$i]['answer']);$k++)
      {
        for ($j=0;$j<count($question[$i]['answer'][$k]);$j++)
        { $question[$i]['key'][$k][$j]=$j;
        };
        for ($j=0;$j<count($question[$i]['answer'][$k]);$j++)
        { $r=rand(0,count($question[$i]['answer'][$k])-1);
          $qt=$question[$i]['key'][$k][$j];
          $question[$i]['key'][$k][$j]=$question[$i]['key'][$k][$r];
          $question[$i]['key'][$k][$r]=$qt;
        };
        $r=rand(1,count($question[$i]['answer'][$k]));
        $ans_str.='   answer['.$i.']['.$k.']=new Array(';
        for ($j=0;$j<count($question[$i]['answer'][$k])-1;$j++)
        {
          $ans_str.='\''.$question[$i]['answer'][$k][$question[$i]['key'][$k][$j]].'\',';
        };
        if (count($question[$i]['answer'][$k])>0)
        {
          $ans_str.='\''.$question[$i]['answer'][$k][$question[$i]['key'][$k][$j]].'\'';
        };
        $ans_str.=");\n";
      };
      $paramStr.=$ans_str;
      $paramStr.=$ans_type_str;
    };
    $paramStr.="\n";
   };

   $formStr.="</form>\n";

   if ($question_skip)
   { $skip_disabled='document.Tcontrols.skip.disabled=true;'."\n";
     $skip_enabled='document.Tcontrols.skip.disabled=false;'."\n";
   }
   else
   { $skip_disabled="\n";
     $skip_enabled="\n";
   };

   $_SESSION['UserSession']['tests'][$id_test]['testCount']=$testCount;
   $_SESSION['UserSession']['tests'][$id_test]['question']=$question;
   $_SESSION['UserSession']['tests'][$id_test]['startTime']=time();

    IF (($_SESSION['UserSession']['group'] != 'admin' && $_SESSION['UserSession']['group'] != 'staff'))
    {
       $query = 'INSERT INTO `user_log` (id_user, ip, action, id_problem) '.
                'VALUES ('.$_SESSION['UserSession']['id_user'].', \''.$_SERVER['REMOTE_ADDR'].'\', \'params\', '.$_SESSION['UserSession']['tests'][$id_test]['id_problem'].')';
       @mysql_query($query,$link);
    };   

    $query = 'select i.last_name, i.first_name, i.second_name from user_info i where i.id_user='.$_SESSION['UserSession']['id_user'];
    $result = mysql_query($query, $link);
    list($name1,$name2,$name3)=mysql_fetch_row($result);
?>

<script language="JavaScript" type="text/javascript">
   var testCurrent=1;
   var lastAnswer=0;
   var testCount=<?=$testCount?>;
   var question=new Array();
   var answer=new Array;
   var answer_type=new Array;
   var isAnswerCorrect=true;
<?=$paramStr?>

   function answerI()
   {
     var aI='<br\/>&nbsp; Ответ: &nbsp; '+answer[testCurrent][2]+'<input type=\"text\" id=\"r1\" size=\"'+answer[testCurrent][0]+'\" value=\"\" onChange=\"checkI(\'ans_'+testCurrent+'\')\" \/> '+answer[testCurrent][1];
     return aI;
   };

   function answerF()
   {
     var aF='<br\/>&nbsp; Ответ: &nbsp; '+answer[testCurrent][2]+'<input type=\"text\" id=\"r1\" size=\"'+answer[testCurrent][0]+'\" value=\"\" onChange=\"checkF(\'ans_'+testCurrent+'\')\" \/> '+answer[testCurrent][1];
     return aF;
   };

   function answerT()
   {
     var aT='<br\/>&nbsp; Ответ: &nbsp; <input type=\"text\" id=\"r1\" value=\"\" onChange=\"checkT(\'ans_'+testCurrent+'\')\" \/>';
     return aT;
   };

   function answerE()
   {
     var aE='<br\/>&nbsp; Ответ: &nbsp; '+answer[testCurrent]+' <input type=\"text\" id=\"r1\" size=\"25\" value=\"\" onChange=\"checkE(\'ans_'+testCurrent+'\')\" \/>';
     return aE;
   };

   function answerC()
   {
     var aC='<br\/>&nbsp; Варианты ответов:\n';
     var N=new Array('0','1','2','3','4','5');
     var r=0;
     var l=answer[testCurrent].length;
     var Ni=0;
     for (var i=1;i<=l;i++)
     {
       r=Math.floor(Math.random()*(l-0.01))+1;
       N[0]=N[i];
       N[i]=N[r];
       N[r]=N[0];
     };
     for (i=1;i<=l;i++)
     {
       Ni=parseInt(N[i])-1;
       if (answer[testCurrent][Ni]=='')
       { aC+='<span style="visibility:hidden">';
         aC+='<input type=\"checkbox\" id=\"r'+N[i]+'\" name=\"r'+N[i]+'\" onClick=\"checkC(\'ans_'+testCurrent+'\')\" \/>';
         aC+='<\/span>\n';
       }
       else
       { aC+='<br\/>&nbsp;<label for=\"r'+N[i]+'\"><input type=\"checkbox\" id=\"r'+N[i]+'\" name=\"r'+N[i]+'\" onClick=\"checkC(\'ans_'+testCurrent+'\')\" \/> ';
         if (answer_type[testCurrent][Ni]=='I')
         {
           aC+='<img src=\"'+answer[testCurrent][Ni]+'\">'
         }
         else if (answer_type[testCurrent][Ni]=='T')
         {
           aC+=answer[testCurrent][Ni];
         };
         aC+='<\/label>\n';
       };
     };
     return aC;
   };

   function answerS()
   {
     var aS='<br\/>&nbsp; Варианты ответов:\n';
     var N=new Array('0','1','2','3','4','5');
     var r=0;
     var l=answer[testCurrent].length;
     var Ni=0;
     for (var i=1;i<=l;i++)
     {
       r=Math.floor(Math.random()*(l-0.01))+1;
       N[0]=N[i];
       N[i]=N[r];
       N[r]=N[0];
     };
     for (i=1;i<=l;i++)
     {
       Ni=parseInt(N[i])-1;
       if (answer[testCurrent][Ni]=='')
       { aS+='<span style="visibility:hidden">';
         aS+='<input type=\"radio\" id=\"r'+N[i]+'\" name=\"rS\" onClick=\"checkS(\'ans_'+testCurrent+'\','+N[i]+')\" \/>';
         aS+='<\/span>\n';
       }
       else
       { aS+='<br\/>&nbsp;<label for=\"r'+N[i]+'\"><input type=\"radio\" id=\"r'+N[i]+'\" name=\"rS\" onClick=\"checkS(\'ans_'+testCurrent+'\','+N[i]+')\" \/>';
         if (answer_type[testCurrent][Ni]=='I')
         {
           aS+='<img src=\"'+answer[testCurrent][Ni]+'\">'
         }
         else if (answer_type[testCurrent][Ni]=='T')
         {
           aS+=answer[testCurrent][Ni];
         };
         aS+='<\/label>\n';
       };
     };
     return aS;
   };

   function optionsL()
   {
     var oL='';
     var N=new Array('0','1','3','5','7','9');
     var r=0;
     var l=answer[testCurrent].length/2;
     var Ni=0;
     for (var i=1;i<=l;i++)
     {
       r=Math.floor(Math.random()*(l-0.01))+1;
       N[0]=N[i];
       N[i]=N[r];
       N[r]=N[0];
     };
     for (i=1;i<=l;i++)
     {
       Ni=parseInt(N[i]);
       oL+='<option value=\"'+N[i]+'\" >'+answer[testCurrent][Ni]+'\n';
     };
     return oL;
   };

   function answerL()
   {
     var aL='<br\/>&nbsp; Варианты ответов:\n';
     var N=new Array('0','0','2','4','6','8');
     var r=0;
     var l=answer[testCurrent].length/2;
     var Ni=0;
     for (var i=1;i<=l;i++)
     {
       r=Math.floor(Math.random()*(l-0.01))+1;
       N[0]=N[i];
       N[i]=N[r];
       N[r]=N[0];
     };
     aL+='<table>\n';
     for (i=1;i<=l;i++)
     {
       Ni=parseInt(N[i]);
       aL+='<tr><td>'+answer[testCurrent][Ni]+'<\/td><td><select id=\"L'+N[i]+'\" onChange=\"selectL(\'ans_'+testCurrent+'\')\">\n';
       aL+='<option value=10 selected>\n';
       aL+=optionsL();
       aL+='<\/select><\/td><\/tr>';
     };
     for (i=l+1;i<=5;i++)
     {
       Ni=parseInt(N[i]);
       aL+='<tr style="visibility:hidden"><td colspan=2><select id=\"L'+N[i]+'\" >\n';
       aL+='<option value=11 selected>\n';
       aL+='<\/select><\/td><\/tr>';
     }
     aL+='<\/table>';
     return aL;
   };

   function answerM()
   {
     return '';
   }

   function answerHTML()
   {
     var aHTML="";
     if (question[testCurrent][0]=="I")
     {
       aHTML=answerI();
     }
     else if (question[testCurrent][0]=="F")
     {
       aHTML=answerF();
     }
     else if (question[testCurrent][0]=="T")
     {
       aHTML=answerT();
     }
     else if (question[testCurrent][0]=="E")
     {
       aHTML=answerE();
     }
     else if (question[testCurrent][0]=="C")
     {
       aHTML=answerC();
     }
     else if (question[testCurrent][0]=="S")
     {
       aHTML=answerS();
     }
     else if (question[testCurrent][0]=="L")
     {
       aHTML=answerL();
     }
     else if (question[testCurrent][0]=="M")
     {
       aHTML=answerM();
     };
     return aHTML;
   };

   function questionW()
   {
     var j=0;
     var inpL='<input type="text" value="" onKeyUp=\"checkW('+testCurrent+')\" id=\"';
     var inpM='\" size=\"';
     var inpR='\">';
     var tmp='';
     for (var i=1;i<=answer[testCurrent].length;i++)
     {
       j=question[testCurrent][1].indexOf('{W'+i+'}');
       if (j>0)
       {
         tmp=inpL+'W'+i+inpM+answer[testCurrent][i-1]+inpR;
         question[testCurrent][1]=question[testCurrent][1].substr(0,j)+tmp+question[testCurrent][1].substr(j+4)
       };
     };
     return '<p>'+question[testCurrent][1]+'<\/p>';
   };

   function questionM()
   {
     var j=0;
     var n=0;
     var selLB='<select onChange=\"checkM('+testCurrent+')\" id=\"';
     var selLE='\">';
     var selR='<\/select>';
     var optL='<option value=\"';
     var optR='\">';
     var tmp='';
     for (var i=1;i<answer[testCurrent].length;i++)
     {
       j=question[testCurrent][1].indexOf('{L'+i+'}');
       if (j>0)
       {
         tmp=selLB+'L'+i+selLE;
         tmp+="\n"+optL+0+optR+'&nbsp;';
         for (n=1;n<=answer[testCurrent][i].length;n++)
         {
           tmp+="\n"+optL+n+optR+answer[testCurrent][i][n-1];
         };
         tmp+=selR;
         question[testCurrent][1]=question[testCurrent][1].substr(0,j)+tmp+question[testCurrent][1].substr(j+4)
       };
     };
     return '<p>'+question[testCurrent][1]+'<\/p>';
   };

   function questionHTML()
   {
     var qHTML="";
     if (String(question[testCurrent][2]).length>0)
     {
       qHTML='<img hspace=5 vspace=5 border=2 src=\"'+question[testCurrent][2]+'\" alt=\"'+question[testCurrent][2]+'\">';
     };

     if (question[testCurrent][0]=="V" || question[testCurrent][0]=="W")
     {
       qHTML+=questionW();
     }
     else if(question[testCurrent][0]=="M")
     {
       qHTML+=questionM();
     }
     else
     {
       qHTML+='<p>'+question[testCurrent][1]+'<\/p>';
     };

     return qHTML;
   };

   function nextTest()
   {
     if (isAnswerCorrect)
     {
       document.Tcontrols.next.disabled=true;
       <?=$skip_disabled?>
       document.Tcontrols.abort.disabled=false;
       testCurrent++;
       lastAnswer++;
       document.answers.lastAnswer.value=lastAnswer;
       if (testCurrent<=testCount)
       {
         var questionSpan=document.getElementById('question');
         var answerSpan=document.getElementById('answer');
         var testNSpan=document.getElementById('testN');
         var testCSpan=document.getElementById('testC');
         questionSpan.innerHTML=questionHTML();
         answerSpan.innerHTML=answerHTML();
         testNSpan.innerHTML=Number(testCurrent).toString();
         testCSpan.innerHTML=Number(testCount-testCurrent+1).toString();
         document.Tcontrols.next.disabled=false;
         <?=$skip_enabled?>
         document.Tcontrols.abort.disabled=false;
         var hframe=document.getElementById('hframe');
         parent.hframe.location.href='../Template/blank.php';
       }
       else
       {
         alert('тестирование окончено');
         document.answers.submit();
       };
     }
     else
     {
       alert('Введите корректный ответ!')
     };
   };

   function abortTest()
   {
     document.Tcontrols.next.disabled=true;
     <?=$skip_disabled?>
     document.Tcontrols.abort.disabled=false;
     document.answers["lastAnswer"].value=lastAnswer;
     if (confirm('прервать тестирование ?'))
     {  document.answers["ans_"+testCurrent].value="";
        document.answers.submit();
     }
     else
     { document.Tcontrols.next.disabled=false;
       <?=$skip_enabled?>
       document.Tcontrols.abort.disabled=false;
     };
   };

<?
if ($question_skip)
{
?>
   function skipTest()
   { document.Tcontrols.skip.disabled=true;
     document.answers["ans_"+testCurrent].value="";
     isAnswerCorrect=true;
     nextTest();
     if (testCurrent<=testCount)
     { document.Tcontrols.skip.disabled=false;
     }
   };

<?
};
?>
    function trim (str)
    {
      while(str!="" && str.charAt(0)==" ")
      { str=str.substr(1,str.length-1);
      };
      while(str!="" && str.charAt(str.length-1)==" ")
      { str=str.substr(0,str.length-2);
      };
      return str;
    };

    function is_Numeric(str)
    {
      isNum=true;
      var i=0
      if(str.charAt(i)=="-")
      { i++;
      };
      for(;i<str.length;i++)
      {
        switch (str.charAt(i))
        {
          case "1":
          case "2":
          case "3":
          case "4":
          case "5":
          case "6":
          case "7":
          case "8":
          case "9":
          case "0":
          case ".":
            continue;
          default:
            isNum=false;
        };
        if (!isNum) break;
      };
      return isNum;
   };

   function checkI(name)
   {
     var r1=document.getElementById('r1');
     r1.value=trim(r1.value);
     if (is_Numeric(r1.value) && (r1.value.indexOf('.')==-1))
     {
       document.answers[name].value=parseInt(r1.value);
       isAnswerCorrect=true;
     }
     else
     {
       alert('Ответ должен быть ввиде целого числа!')
       isAnswerCorrect=false;
     };
   };

   function checkF(name)
   {
     var r1=document.getElementById('r1');
     r1.value=trim(r1.value);
     var c=r1.value.split(',');
     r1.value=c[0];
     for (var i=1;i<c.length;i++)
     { r1.value+="."+c[i];
     };
     if (is_Numeric(r1.value))
     {
       document.answers[name].value=parseFloat(r1.value);
       isAnswerCorrect=true;
     }
     else
     {
       alert('Ответ должен быть ввиде вещественного числа!')
       isAnswerCorrect=false;
     };
   };

   function checkT(name)
   {
     var r1=document.getElementById('r1');
     document.answers[name].value=r1.value;
   };

   function checkE(name)
   {
     var r1=document.getElementById('r1');
     document.answers[name].value=r1.value;
   };

   function checkC(name)
   {
     var r1=document.getElementById('r1');
     var r2=document.getElementById('r2');
     var r3=document.getElementById('r3');
     var r4=document.getElementById('r4');
     var r5=document.getElementById('r5');
     document.answers[name].value=
       "ans_A[1]="+r1.checked+";"+
       "ans_A[2]="+r2.checked+";"+
       "ans_A[3]="+r3.checked+";"+
       "ans_A[4]="+r4.checked+";"+
       "ans_A[5]="+r5.checked+";";
   };

   function checkS(name,N)
   {document.answers[name].value="ans_A["+N+"]=true;"
   };

   function selectL(name)
   {
     var L0=document.getElementById('L0');
     var L2=document.getElementById('L2');
     var L4=document.getElementById('L4');
     var L6=document.getElementById('L6');
     var L8=document.getElementById('L8');
     document.answers[name].value=
       "ans_A[1]="+L0.value+";"+
       "ans_A[2]="+L2.value+";"+
       "ans_A[3]="+L4.value+";"+
       "ans_A[4]="+L6.value+";"+
       "ans_A[5]="+L8.value+";";
   };

   function checkW(ID)
   {
     var name='ans_'+ID;
     var W=null;
     document.answers[name].value='';
     for(var i=0;i<=answer[ID].length;i++)
     {
       W=document.getElementById('W'+i);
       if(W!=null)
       {
         document.answers[name].value=document.answers[name].value+"ans_A["+i+"]=\'"+W.value+"\';";
       };
     };
   };

   function checkM(ID)
   {
     var name='ans_'+ID;
     var L=null;
     document.answers[name].value='';
     for(var i=0;i<=answer[ID].length;i++)
     {
       L=document.getElementById('L'+i);
       if(L!=null)
       {
         document.answers[name].value=document.answers[name].value+"ans_A["+i+"]="+L.value+";"
       };
     };
   };

   function testLoad()
   {
     var l=question.length-1;
     var tq=0;
     var r=0;

     var questionSpan=document.getElementById('question');
     var answerSpan=document.getElementById('answer');
     var testCountSpan=document.getElementById('testCount');
     var testNSpan=document.getElementById('testN');
     var testCSpan=document.getElementById('testC');
     questionSpan.innerHTML=questionHTML();
     answerSpan.innerHTML=answerHTML();
     testNSpan.innerHTML=Number(testCurrent).toString();
     testCountSpan.innerHTML=Number(testCount).toString();
     testCSpan.innerHTML=Number(testCount-testCurrent+1).toString();
     document.Tcontrols.next.disabled=false;
     <?=$skip_enabled?>
     document.Tcontrols.abort.disabled=false;
   };
 </script>

<div style="white-space: nowrap;">Название теста: <font size=+1><b id="testName"><?=$test_name?></b></font></div>
<div style="white-space: nowrap;">Задание: <font size=+1><b><span id="testN">0</span></b></font>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Всего заданий: <font size=+1><b><span id="testCount">0</span></b></font>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Осталось заданий: <font size=+1><b><span id="testC">0</span></b></font>
</div>
<br/>
<table align="center" cellpadding="2" cellspacing="1" width="100%" bgcolor="#000000">
 <tr>
   <td vAlign="top" bgcolor="#ffffff" style="padding:8px">
   <div id="question">
   </div>
   <div id="answer">
   </div>
   <br/>
   <form name="Tcontrols" action="">
     <div align="center">
       <input type="button" name="next" value="Ответить" onclick="nextTest();" disabled="disabled" />
<?
      if ($question_skip)
      {
?>
       &nbsp;&nbsp;<input type="button" name="skip" value="Пропустить" onclick="skipTest();" disabled="disabled" />
<?
      };
?>
     </div>
     <div align="right">
       <input type="button" name="abort" value="Прервать работу" onclick="abortTest();" disabled="disabled" />
     </div>
   </form>
   </td>
 </tr>
</table>

<iframe name="hframe" style="visibility:hidden; position:absolute" src="../Template/blank.php">
</iframe>

<div align="center"><font face="Arial" color="black" size=1>v 1.8 &copy; Copyright 2006-2009 L.A.Evstigneev, V.V.Monakhov </font></div>
<?=$formStr?>
<script language="JavaScript" type="text/javascript">
/*<![CDATA[*/
  testLoad();
/*]]>*/
</script>
<?
};
include "../Template/Bottom.php";
mysql_close($link);
?>

