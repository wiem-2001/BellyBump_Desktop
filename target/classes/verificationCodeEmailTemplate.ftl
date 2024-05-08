<!DOCTYPE html>
<html>
<head>
    <title>Password Reset</title>
    <style>
        /* Add styles for the pink rectangle */
        .pink-rectangle {
            background-color: #FFC0CB; /* Pink color */
            color: white; /* White text */
            padding: 10px; /* Add padding */
            font-size: 24px; /* Adjust font size */
            text-align: center; /* Center text */
        }
    </style>
</head>
<body>
<div class="pink-rectangle">BellyBump</div>
<p style="font-size: 18px">
    Dear ${userName},<br><br>
    Thank you for using <strong>BellyBump</strong>.<br/><br/>
    Your verification code is: <span style="color: #B22222; font-weight: bolder" >${verificationCode}</span><br/><br/>
    If you did not request this verification code, please ignore this email.<br/><br/>
    Best regards,<br/>
    - BellyBump
</p>
</body>
</html>
