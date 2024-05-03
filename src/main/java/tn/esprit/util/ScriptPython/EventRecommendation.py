import numpy as np
import json,sys
from sklearn.metrics.pairwise import cosine_similarity
import re

def string_to_set(s):
    key_value_pairs = re.findall(r'(\d+):(\[\d*(?:,\s*\d+)*\])', s)
    result_set = {int(k): eval(v) for k, v in key_value_pairs}
    return result_set

def recommend_events(user_profiles_serialized, user_id):
    user_profiles = string_to_set(user_profiles_serialized)
    user_id = int(user_id)
    if user_id not in user_profiles:
        return {'error': 'User ID not found'}
    
    user_profile = user_profiles[user_id]
    all_events = set()
    for events in user_profiles.values():
        all_events.update(events)

    similarities = {}
    for other_user, profile in user_profiles.items():
        if other_user != user_id:
            vector1 = np.array([1 if event in user_profile else 0 for event in all_events])
            vector2 = np.array([1 if event in profile else 0 for event in all_events])
            similarity = cosine_similarity([vector1], [vector2])[0][0]
            similarities[other_user] = similarity

    recommendations = set()
    for other_user, similarity in sorted(similarities.items(), key=lambda x: x[1], reverse=True):
        if similarity > 0:  # Similar user
            recommendations.update(set(user_profiles[other_user]) - set(user_profile))
    
    recommendations -= set(user_profile)
    
    return recommendations

recommend_events = recommend_events(sys.argv[1],sys.argv[2])
print(list(recommend_events))
